package kr.techit.lion.presentation.schedule

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kr.techit.lion.domain.model.ScheduleDetail
import kr.techit.lion.presentation.R
import kr.techit.lion.presentation.compose.screen.intro.navigation.route.IntroRoute
import kr.techit.lion.presentation.compose.screen.intro.IntroActivity
import kr.techit.lion.presentation.compose.screen.intro.login.vm.model.LogInStatus
import kr.techit.lion.presentation.databinding.ActivityScheduleDetailBinding
import kr.techit.lion.presentation.delegate.NetworkState
import kr.techit.lion.presentation.ext.repeatOnStarted
import kr.techit.lion.presentation.ext.setImageSmall
import kr.techit.lion.presentation.ext.showPhotoDialog
import kr.techit.lion.presentation.ext.showSnackbar
import kr.techit.lion.presentation.home.DetailActivity
import kr.techit.lion.presentation.main.dialog.ConfirmDialog
import kr.techit.lion.presentation.connectivity.ConnectivityObserver
import kr.techit.lion.presentation.connectivity.NetworkConnectivityObserver
import kr.techit.lion.presentation.report.ReportActivity
import kr.techit.lion.presentation.schedule.ResultCode.RESULT_REVIEW_EDIT
import kr.techit.lion.presentation.schedule.ResultCode.RESULT_REVIEW_WRITE
import kr.techit.lion.presentation.schedule.ResultCode.RESULT_SCHEDULE_EDIT
import kr.techit.lion.presentation.schedule.adapter.ScheduleImageViewPagerAdapter
import kr.techit.lion.presentation.schedule.adapter.ScheduleListAdapter
import kr.techit.lion.presentation.schedule.customview.ScheduleManageBottomSheet
import kr.techit.lion.presentation.schedule.customview.ScheduleReviewManageBottomSheet
import kr.techit.lion.presentation.schedule.vm.ScheduleDetailViewModel
import kr.techit.lion.presentation.scheduleform.ModifyScheduleFormActivity
import kr.techit.lion.presentation.schedulereview.ModifyScheduleReviewActivity
import kr.techit.lion.presentation.schedulereview.WriteScheduleReviewActivity
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate

@AndroidEntryPoint
class ScheduleDetailActivity : AppCompatActivity() {

    private val viewModel: ScheduleDetailViewModel by viewModels()

    private val binding: ActivityScheduleDetailBinding by lazy {
        ActivityScheduleDetailBinding.inflate(layoutInflater)
    }

    private val connectivityObserver: ConnectivityObserver by lazy {
        NetworkConnectivityObserver(applicationContext)
    }

    private val scheduleReviewLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val planId = intent.getLongExtra("planId", -1)
            viewModel.getScheduleDetailInfo(planId)
            when (result.resultCode) {
                RESULT_REVIEW_WRITE -> {
                    binding.root.showSnackbar("후기가 저장되었습니다")
                }

                RESULT_SCHEDULE_EDIT -> {
                    binding.root.showSnackbar("일정이 수정되었습니다")
                }

                RESULT_REVIEW_EDIT -> {
                    binding.root.showSnackbar("리뷰가 수정되었습니다")
                }
            }
        }

    private val reportLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                RESULT_OK -> {
                    binding.root.showSnackbar("신고가 완료되었습니다")
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        setSnackBar()
        observeDeleteMyPlan()

        repeatOnStarted {
            launch { collectScheduleDetailState() }
            launch { observeConnectivity() }
        }
    }

    private suspend fun observeConnectivity() {
        with(binding) {
            connectivityObserver.getFlow().collect { connectivity ->
                when (connectivity) {
                    ConnectivityObserver.Status.Available -> {
                        scheduleDetailErrorLayout.visibility = View.GONE
                        scheduleDetailLayout.visibility = View.VISIBLE
                        val planId = intent.getLongExtra("planId", -1)
                        when (viewModel.loginState.value) {
                            LogInStatus.LoggedIn -> {
                                viewModel.getScheduleDetailInfo(planId)
                            }

                            LogInStatus.LoginRequired -> {
                                viewModel.getScheduleDetailInfoGuest(planId)
                            }

                            LogInStatus.Checking -> {
                                return@collect
                            }
                        }
                    }

                    ConnectivityObserver.Status.Unavailable,
                    ConnectivityObserver.Status.Losing,
                    ConnectivityObserver.Status.Lost -> {
                        scheduleDetailErrorLayout.visibility = View.VISIBLE
                        scheduleDetailLayout.visibility = View.GONE
                        val msg = "${getString(R.string.text_network_is_unavailable)}\n" +
                                "${getString(R.string.text_plz_check_network)} "
                        scheduleDetailErrorMsg.text = msg
                    }
                }

            }
        }
    }

    private fun initView(isUser: Boolean, planId: Long) {
        with(binding) {

            viewModel.scheduleDetail.observe(this@ScheduleDetailActivity) { scheduleDetail ->

                initToolbarMenu(
                    isUser,
                    scheduleDetail.isWriter,
                    scheduleDetail.isPublic,
                    scheduleDetail.isBookmark,
                    scheduleDetail.isReport,
                    planId
                )

                settingScheduleAdapter(scheduleDetail)
                schedulePublic.visibility = View.VISIBLE

                textViewReview.visibility = View.VISIBLE

                scheduleDetail.remainDate?.let {
                    scheduleDday.text = it
                    scheduleDday.visibility = View.VISIBLE

                    cardViewScheduleEmptyReview.visibility = View.VISIBLE

                    // 지나가지 않은 일정 + 내가 작성자
                    if (scheduleDetail.isWriter) {
                        this.scheduleEmptyReviewTitle.text =
                            getString(R.string.text_schedule_info_writer_not_leave_title)
                        this.scheduleEmptyReviewContent.text =
                            getString(R.string.text_schedule_info_writer_not_leave_content)
                    }
                    // 지나가지 않은 일정 + 내가 작성자가 아님
                    else {
                        this.scheduleEmptyReviewTitle.text =
                            getString(R.string.text_schedule_info_not_leave_title)
                        this.scheduleEmptyReviewContent.text =
                            getString(R.string.text_schedule_info_not_leave_content)
                    }
                } ?: run {
                    // 지나간 일정
                    scheduleDday.visibility = View.GONE

                    // 리뷰가 있을때,
                    if (scheduleDetail.hasReview) {
                        // 신고 O
                        if (scheduleDetail.isReport == true) {
                            cardViewScheduleReview.visibility = View.GONE
                            cardViewScheduleEmptyReview.visibility = View.GONE
                            cardViewReportReview.visibility = View.VISIBLE
                        } else {
                            // 신고 O
                            val planId = intent.getLongExtra("planId", -1)
                            scheduleDetail.reviewId?.let {
                                initReviewMenu(scheduleDetail.isWriter, isUser, planId, it)
                            }

                            cardViewScheduleReview.visibility = View.VISIBLE
                            cardViewScheduleEmptyReview.visibility = View.GONE
                            this@ScheduleDetailActivity.setImageSmall(
                                ivProfileImage,
                                scheduleDetail.profileUrl
                            )
                            textNickname.text = scheduleDetail.nickname
                            ratingBarScheduleSatisfaction.rating =
                                scheduleDetail.grade?.toFloat() ?: 0F
                            textViewScheduleReviewContent.text = scheduleDetail.content

                            scheduleDetail.reviewImages?.let { reviewImages ->
                                when (reviewImages.size) {
                                    1 -> {
                                        scheduleReviewImg1.visibility = View.VISIBLE
                                        this@ScheduleDetailActivity.setImageSmall(
                                            scheduleReviewImg1,
                                            reviewImages[0]
                                        )
                                    }

                                    2 -> {
                                        scheduleReviewImg1.visibility = View.VISIBLE
                                        scheduleReviewImg2.visibility = View.VISIBLE
                                        this@ScheduleDetailActivity.setImageSmall(
                                            scheduleReviewImg1,
                                            reviewImages[0]
                                        )
                                        this@ScheduleDetailActivity.setImageSmall(
                                            scheduleReviewImg2,
                                            reviewImages[1]
                                        )
                                    }

                                    3 -> {
                                        scheduleReviewImg1.visibility = View.VISIBLE
                                        scheduleReviewImg2.visibility = View.VISIBLE
                                        scheduleReviewImg3.visibility = View.VISIBLE
                                        this@ScheduleDetailActivity.setImageSmall(
                                            scheduleReviewImg1,
                                            reviewImages[0]
                                        )
                                        this@ScheduleDetailActivity.setImageSmall(
                                            scheduleReviewImg2,
                                            reviewImages[1]
                                        )
                                        this@ScheduleDetailActivity.setImageSmall(
                                            scheduleReviewImg3,
                                            reviewImages[2]
                                        )
                                    }

                                    4 -> {
                                        scheduleReviewImg1.visibility = View.VISIBLE
                                        scheduleReviewImg2.visibility = View.VISIBLE
                                        scheduleReviewImg3.visibility = View.VISIBLE
                                        scheduleReviewImg4.visibility = View.VISIBLE
                                        this@ScheduleDetailActivity.setImageSmall(
                                            scheduleReviewImg1,
                                            reviewImages[0]
                                        )
                                        this@ScheduleDetailActivity.setImageSmall(
                                            scheduleReviewImg2,
                                            reviewImages[1]
                                        )
                                        this@ScheduleDetailActivity.setImageSmall(
                                            scheduleReviewImg3,
                                            reviewImages[2]
                                        )
                                        this@ScheduleDetailActivity.setImageSmall(
                                            scheduleReviewImg4,
                                            reviewImages[3]
                                        )
                                    }
                                }
                            }

                            scheduleReviewImg1.setOnClickListener {
                                scheduleDetail.reviewImages?.let { reviewImages ->
                                    this@ScheduleDetailActivity.baseContext.showPhotoDialog(
                                        this@ScheduleDetailActivity.supportFragmentManager,
                                        reviewImages,
                                        0
                                    )
                                }
                            }

                            scheduleReviewImg2.setOnClickListener {
                                scheduleDetail.reviewImages?.let { reviewImages ->
                                    this@ScheduleDetailActivity.baseContext.showPhotoDialog(
                                        this@ScheduleDetailActivity.supportFragmentManager,
                                        reviewImages,
                                        1
                                    )
                                }
                            }

                            scheduleReviewImg3.setOnClickListener {
                                scheduleDetail.reviewImages?.let { reviewImages ->
                                    this@ScheduleDetailActivity.baseContext.showPhotoDialog(
                                        this@ScheduleDetailActivity.supportFragmentManager,
                                        reviewImages,
                                        2
                                    )
                                }
                            }

                            scheduleReviewImg4.setOnClickListener {
                                scheduleDetail.reviewImages?.let { reviewImages ->
                                    this@ScheduleDetailActivity.baseContext.showPhotoDialog(
                                        this@ScheduleDetailActivity.supportFragmentManager,
                                        reviewImages,
                                        3
                                    )
                                }
                            }
                        }
                    }

                    // 리뷰가 없을 때
                    else {
                        if (scheduleDetail.isWriter) {
                            cardViewScheduleReview.visibility = View.GONE
                            cardViewScheduleEmptyReview.visibility = View.VISIBLE
                            this.scheduleEmptyReviewTitle.text = getString(R.string.text_my_review)
                            this.scheduleEmptyReviewContent.text =
                                getString(R.string.text_leave_schedule_review)

                            cardViewScheduleEmptyReview.setOnClickListener {
                                val newIntent =
                                    Intent(
                                        this@ScheduleDetailActivity,
                                        WriteScheduleReviewActivity::class.java
                                    )
                                val planId = intent.getLongExtra("planId", -1)
                                newIntent.putExtra("planId", planId)
                                scheduleReviewLauncher.launch(newIntent)
                            }
                        } else {
                            cardViewScheduleEmptyReview.visibility = View.VISIBLE
                            this.scheduleEmptyReviewTitle.text =
                                getString(R.string.text_schedule_info_leave_title)
                            this.scheduleEmptyReviewContent.text =
                                getString(R.string.text_schedule_info_leave_content)
                        }
                    }

                }

                if (scheduleDetail.isReport == true) {
                    schedulePublic.text = getString(R.string.text_schedule_private)
                } else {
                    if (scheduleDetail.isPublic) {
                        schedulePublic.text = getString(R.string.text_schedule_public)
                    } else {
                        schedulePublic.text = getString(R.string.text_schedule_private)
                    }
                }

                textViewScheduleName.text = scheduleDetail.title
                textViewSchedulePeriod.text = getString(
                    R.string.text_schedule_period,
                    scheduleDetail.startDate,
                    scheduleDetail.endDate
                )

                scheduleDetail.images?.let {
                    initImageViewPager(this@ScheduleDetailActivity, it)
                }
            }
        }

        binding.toolbarError.setNavigationOnClickListener {
            finish()
        }
    }

    private suspend fun collectScheduleDetailState() {
        val planId = intent.getLongExtra("planId", -1)
        with(binding) {
            repeatOnStarted {

                viewModel.networkState.combine(viewModel.loginState) { networkState, loginState ->
                    networkState to loginState
                }.collect { (networkState, loginState) ->

                    when (networkState) {
                        is NetworkState.Loading -> {
                            scheduleDetailProgressBar.visibility = View.VISIBLE
                            scheduleDetailErrorLayout.visibility = View.GONE
                            scheduleDetailLayout.visibility = View.GONE
                        }

                        is NetworkState.Success -> {
                            scheduleDetailProgressBar.visibility = View.GONE
                            scheduleDetailErrorLayout.visibility = View.GONE
                            scheduleDetailLayout.visibility = View.VISIBLE
                        }

                        is NetworkState.Error -> {
                            if (viewModel.deletePlanSuccess.value == false) {
                                binding.root.showSnackbar(networkState.msg)
                            } else if (viewModel.deleteReviewSuccess.value == false) {
                                binding.root.showSnackbar(networkState.msg)
                            } else if (viewModel.updatePublicSuccess.value == false) {
                                binding.root.showSnackbar(networkState.msg)
                            } else if (viewModel.updateBookmarkSuccess.value == false) {
                                binding.root.showSnackbar(networkState.msg)
                            } else {
                                scheduleDetailProgressBar.visibility = View.GONE
                                scheduleDetailErrorLayout.visibility = View.VISIBLE
                                scheduleDetailLayout.visibility = View.GONE
                                scheduleDetailErrorMsg.text = networkState.msg
                            }
                        }
                    }


                    when (loginState) {
                        is LogInStatus.Checking -> {
                            return@collect
                        }

                        is LogInStatus.LoggedIn -> {
                            viewModel.getScheduleDetailInfo(planId)
                            initView(true, planId)
                        }

                        is LogInStatus.LoginRequired -> {
                            viewModel.getScheduleDetailInfoGuest(planId)
                            initView(false, planId)
                        }
                    }
                }
            }
        }
    }

    private fun setSnackBar() {
        viewModel.snackbarSuccessMessage.observe(this) { message ->
            message?.let {
                binding.root.showSnackbar(it)
            }
        }
    }

    private fun observeDeleteMyPlan() {
        viewModel.deletePlanSuccess.observe(this) { isSuccess ->
            if (isSuccess) {
                setResult(RESULT_OK)
                finish()
            }
        }
    }

    private fun initImageViewPager(context: Context, images: List<String>) {
        val imageList: List<String> = if (images.isEmpty()) {
            val drawableId = R.drawable.empty_view
            val uri = Uri.parse("android.resource://${context.packageName}/$drawableId").toString()
            listOf(uri)
        } else {
            images
        }

        binding.viewPagerScheduleImages.apply {
            val scheduleAdpater = ScheduleImageViewPagerAdapter(imageList)
            adapter = scheduleAdpater
            startAutoSlide(scheduleAdpater)
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }

    }

    private fun startAutoSlide(adpater: ScheduleImageViewPagerAdapter) {
        val timer = Timer()
        val handler = Handler(Looper.getMainLooper())

        // 일정 간격으로 슬라이드 변경 (4초마다)
        timer.scheduleAtFixedRate(3000, 4000) {
            handler.post {
                val nextItem = binding.viewPagerScheduleImages.currentItem + 1
                if (nextItem < adpater.itemCount) {
                    binding.viewPagerScheduleImages.currentItem = nextItem
                } else {
                    binding.viewPagerScheduleImages.currentItem = 0 // 마지막 페이지에서 첫 페이지로 순환
                }
            }
        }
    }

    private fun initToolbarMenu(
        isUser: Boolean,
        isWriter: Boolean,
        isPublic: Boolean,
        isBookmark: Boolean,
        isReport: Boolean?,
        planId: Long
    ) {

        with(binding.toolbarViewSchedule) {
            menu.clear()
            setNavigationOnClickListener {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
            if (isWriter) { // 로그인한 사용자의 일정인 경우
                inflateMenu(R.menu.menu_schedule_private)
                binding.textViewScheduleType.text = getString(R.string.text_my_schedule)
                setOnMenuItemClickListener {
                    showScheduleManageBottomSheet(isPublic, isReport)
                    true
                }

            } else { // 로그인 여부와 상관없이 타인의 일정인 경우
                binding.textViewScheduleType.text = getString(R.string.text_public_schedule)
                inflateMenu(R.menu.menu_schedule_public)
                val menuItemBookmark =
                    binding.toolbarViewSchedule.menu.findItem(R.id.menuSchedulPublicBookmark)
                setBookmarkIcon(menuItemBookmark, isBookmark)
                setOnMenuItemClickListener {
                    if (isUser) { // 로그인한 사용자
                        viewModel.updateScheduleDetailBookmark(planId)
                    } else {
                        displayLoginDialog("여행 일정을 북마크하고 싶다면\n로그인을 진행해주세요")
                    }
                    true
                }
            }
        }
    }

    private fun setBookmarkIcon(menuItem: MenuItem, isBookmark: Boolean) {
        if (isBookmark) {
            menuItem.setIcon(R.drawable.bookmark_fill_scheduledetail_icon)
        } else {
            menuItem.setIcon(R.drawable.bookmark_schedule_detail_icon)
        }
    }

    private fun initReviewMenu(isWriter: Boolean, isUser: Boolean, planId: Long, reviewId: Long) {
        if (isWriter) {
            with(binding.imageButtonScheduleManageReview) {
                visibility = View.VISIBLE
                setOnClickListener {
                    showScheduleReviewManageBottomSheet(
                        planId = planId,
                        reviewId = reviewId
                    )
                }
            }
        } else {
            with(binding.buttonScheduleReportReview) {
                visibility = View.VISIBLE
                setOnClickListener {
                    if (isUser) {
                        val newIntent = Intent(
                            this@ScheduleDetailActivity,
                            ReportActivity::class.java
                        ).apply {
                            putExtra("reviewType", "PlanReview")
                            putExtra("reviewId", reviewId)
                        }
                        reportLauncher.launch(newIntent)
                    } else {
                        displayLoginDialog("여행 후기를 신고하고 싶다면\n로그인을 진행해주세요")
                    }
                }
            }
        }
    }

    private fun displayLoginDialog(subtitle: String) {
        val dialog = ConfirmDialog(
            "로그인이 필요해요!",
            subtitle,
            "로그인하기",
        ) {
            val intent = Intent(this@ScheduleDetailActivity, IntroActivity::class.java).apply {
                putExtra("destination", IntroRoute.Login.route)
            }
            startActivity(intent)
        }
        dialog.isCancelable = true
        dialog.show(supportFragmentManager, "ScheduleLoginDialog")
    }

    private fun settingScheduleAdapter(scheduleDetail: ScheduleDetail) {
        binding.rvScheduleFullList.adapter = ScheduleListAdapter(
            scheduleDetail.dailyPlans,
            scheduleListListener = { schedulePosition, placePosition ->
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra(
                    "detailPlaceId",
                    scheduleDetail.dailyPlans[schedulePosition].schedulePlaces[placePosition].placeId
                )
                startActivity(intent)
            })
    }

    private fun showScheduleManageBottomSheet(isPublic: Boolean, isReport: Boolean?) {

        val planId = intent.getLongExtra("planId", -1)

        ScheduleManageBottomSheet(
            isPublic = isPublic,
            isReport = isReport,
            onScheduleStateToggleListener = {
                // 공개/비공개 상태 Toggle Listener
                // 공개 -> 비공개
                viewModel.updateMyPlanPublic(planId)
            },
            onScheduleDeleteClickListener = {
                viewModel.deleteMyPlanSchedule(planId)

            },
            onScheduleEditClickListener = {
                // 일정 수정할 때 필요한 정보만 분리 (ViewModel에서 데이터 처리)
                val scheduleInfo = viewModel.selectDataForModification(planId)
                val newIntent =
                    Intent(this@ScheduleDetailActivity, ModifyScheduleFormActivity::class.java)
                newIntent.putExtra("scheduleInfo", scheduleInfo)
                scheduleReviewLauncher.launch(newIntent)
            }).show(supportFragmentManager, "ScheduleManageBottomSheet")
    }

    private fun showScheduleReviewManageBottomSheet(planId: Long, reviewId: Long) {
        ScheduleReviewManageBottomSheet(
            onReviewDeleteClickListener = {
                viewModel.deleteMyPlanReview(
                    reviewId = reviewId,
                    planId = planId
                )
            },
            onReviewEditClickListener = {
                val reviewInfo = viewModel.selectReviewDataForModification()
                val newIntent =
                    Intent(this@ScheduleDetailActivity, ModifyScheduleReviewActivity::class.java)
                newIntent.putExtra("reviewInfo", reviewInfo)
                scheduleReviewLauncher.launch(newIntent)
            }).show(supportFragmentManager, "ScheduleReviewManageBottomSheet")
    }
}