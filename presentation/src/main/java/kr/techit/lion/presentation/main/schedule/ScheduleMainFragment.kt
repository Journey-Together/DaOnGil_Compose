package kr.techit.lion.presentation.main.schedule

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kr.techit.lion.domain.model.MyMainSchedule
import kr.techit.lion.presentation.R
import kr.techit.lion.presentation.compose.screen.intro.navigation.route.IntroRoute
import kr.techit.lion.presentation.compose.screen.intro.IntroActivity
import kr.techit.lion.presentation.compose.screen.intro.login.vm.model.LogInStatus
import kr.techit.lion.presentation.scheduleform.ScheduleFormActivity
import kr.techit.lion.presentation.databinding.FragmentScheduleMainBinding
import kr.techit.lion.presentation.delegate.NetworkState
import kr.techit.lion.presentation.ext.repeatOnViewStarted
import kr.techit.lion.presentation.ext.showSnackbar
import kr.techit.lion.presentation.main.adapter.ScheduleMyAdapter
import kr.techit.lion.presentation.main.adapter.SchedulePublicAdapter
import kr.techit.lion.presentation.main.dialog.ConfirmDialog
import kr.techit.lion.presentation.main.schedule.vm.ScheduleMainViewModel
import kr.techit.lion.presentation.myschedule.MyScheduleActivity
import kr.techit.lion.presentation.connectivity.ConnectivityObserver
import kr.techit.lion.presentation.connectivity.NetworkConnectivityObserver
import kr.techit.lion.presentation.schedule.PublicScheduleActivity
import kr.techit.lion.presentation.schedule.ResultCode
import kr.techit.lion.presentation.schedulereview.WriteScheduleReviewActivity
import kr.techit.lion.presentation.schedule.ScheduleDetailActivity

@AndroidEntryPoint
class ScheduleMainFragment : Fragment(R.layout.fragment_schedule_main) {
    private var isUser = true

    private val viewModel: ScheduleMainViewModel by viewModels()
    private val connectivityObserver: ConnectivityObserver by lazy {
        NetworkConnectivityObserver(requireContext().applicationContext)
    }

    private val scheduleReviewLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == ResultCode.RESULT_REVIEW_WRITE) {
            viewModel.getScheduleMainLists()
            view?.showSnackbar("후기가 저장되었습니다", Snackbar.LENGTH_LONG)
        }
    }

    private val scheduleFormLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == ResultCode.RESULT_SCHEDULE_EDIT) {
            viewModel.getScheduleMainLists()
            view?.showSnackbar("일정이 저장되었습니다", Snackbar.LENGTH_LONG)
        }
    }

    private val scheduleDetailLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            view?.showSnackbar("일정이 삭제되었습니다", Snackbar.LENGTH_LONG)
            viewModel.getScheduleMainLists()
        } else {
            if(isUser){
                viewModel.getScheduleMainLists()
            } else {
                viewModel.getOpenPlanList()
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentScheduleMainBinding.bind(view)

        settingRecyclerView(binding)
        initButtonClickListener(binding)

        repeatOnViewStarted {
                launch { collectScheduleMainState(binding) }
                launch { observeConnectivity(binding) }
        }
    }

    private suspend fun collectScheduleMainState(binding: FragmentScheduleMainBinding){
        with(binding){
            viewModel.networkState.combine(viewModel.loginState) { networkState, loginState ->
                networkState to loginState
            }.collect { (networkState, loginState) ->

                when (networkState) {
                    is NetworkState.Loading -> {
                        scheduleMainProgressBar.visibility = View.VISIBLE
                        scheduleMainErrorLayout.visibility = View.GONE
                        scheduleMainLayout.visibility = View.GONE
                    }
                    is NetworkState.Success -> {
                        scheduleMainProgressBar.visibility = View.GONE
                        scheduleMainErrorLayout.visibility = View.GONE
                        scheduleMainLayout.visibility = View.VISIBLE
                    }
                    is NetworkState.Error -> {
                        scheduleMainProgressBar.visibility = View.GONE
                        scheduleMainErrorLayout.visibility = View.VISIBLE
                        scheduleMainLayout.visibility = View.GONE
                        scheduleMainErrorMsg.text = networkState.msg
                    }
                }


                when (loginState) {
                    LogInStatus.Checking -> return@collect
                    LogInStatus.LoggedIn -> {
                        isUser = true
                        viewModel.getScheduleMainLists()
                        binding.textViewMyScheduleMore.visibility = View.VISIBLE
                        binding.recyclerViewMySchedule.visibility = View.VISIBLE
                        binding.cardViewEmptySchedule.visibility = View.GONE
                    }

                    LogInStatus.LoginRequired -> {
                        isUser = false
                        viewModel.getOpenPlanList()
                        binding.textViewMyScheduleMore.visibility = View.INVISIBLE
                        binding.recyclerViewMySchedule.visibility = View.GONE
                        binding.cardViewEmptySchedule.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private suspend fun observeConnectivity(binding: FragmentScheduleMainBinding) {
        with(binding){
            connectivityObserver.getFlow().collect { connectivity ->
                when(connectivity){
                    ConnectivityObserver.Status.Available -> {
                        scheduleMainErrorLayout.visibility = View.GONE
                        scheduleMainLayout.visibility = View.VISIBLE
                        if (viewModel.networkState.value is NetworkState.Error) {
                            viewModel.getOpenPlanList()
                            if(isUser){
                                viewModel.getScheduleMainLists()
                            }
                        }
                    }
                    ConnectivityObserver.Status.Unavailable,
                    ConnectivityObserver.Status.Losing,
                    ConnectivityObserver.Status.Lost -> {
                        scheduleMainLayout.visibility = View.GONE
                        scheduleMainErrorLayout.visibility = View.VISIBLE
                        val msg = "${getString(R.string.text_network_is_unavailable)}\n" +
                                "${getString(R.string.text_plz_check_network)} "
                        scheduleMainErrorMsg.text = msg
                    }
                }
            }
        }
    }

    private fun settingRecyclerView(binding: FragmentScheduleMainBinding) {

        with(binding) {
            viewModel.myMainPlanList.observe(viewLifecycleOwner) {
                if (it.isNullOrEmpty()) {
                    binding.recyclerViewMySchedule.visibility = View.GONE
                    binding.cardViewEmptySchedule.visibility = View.VISIBLE
                    return@observe
                }

                binding.recyclerViewMySchedule.visibility = View.VISIBLE
                binding.cardViewEmptySchedule.visibility = View.GONE

                recyclerViewMySchedule.apply {
                    val myscheduleAdapter = ScheduleMyAdapter(
                        itemClickListener = { position ->
                            // to do - 여행 일정 idx 전달
                            val planId = it[position]?.planId
                            planId?.let {
                                initScheduleDetailActivity(it)
                            }
                        },
                        reviewClickListener = { position ->
                            val intent = Intent(requireActivity(), WriteScheduleReviewActivity::class.java)
                            intent.putExtra("planId", it[position]?.planId)
                            scheduleReviewLauncher.launch(intent)
                        }
                    )
                    myscheduleAdapter.addItems(it as List<MyMainSchedule>)
                    adapter = myscheduleAdapter

                    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }

            }

            // 공개 일정
            recyclerViewPublicSchedule.apply {
                viewModel.openPlanList.observe(viewLifecycleOwner) {
                    val schedulePublicAdapter = SchedulePublicAdapter(
                        itemClickListener = { position ->
                            // to do - 여행 일정 idx 전달
                            val planId = it[position].planId
                            planId.let {
                                initScheduleDetailActivity(it)
                            }
                        }
                    )
                    schedulePublicAdapter.addItems(it)
                    adapter = schedulePublicAdapter
                }
            }
        }
    }

    private fun initButtonClickListener(binding: FragmentScheduleMainBinding) {
        with(binding) {
            buttonAddSchedule.setOnClickListener {
                createNewSchedule()
            }
            textViewAddSchedule.setOnClickListener {
                createNewSchedule()
            }
            textViewMyScheduleMore.setOnClickListener {
                val intent = Intent(requireActivity(), MyScheduleActivity::class.java)
                startActivity(intent)
            }
            textViewPublicScheduleMore.setOnClickListener {
                // 공개 일정 더보기
                val intent = Intent(requireActivity(), PublicScheduleActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun createNewSchedule() {
        if (isUser) {
            val intent = Intent(requireActivity(), ScheduleFormActivity::class.java)
            scheduleFormLauncher.launch(intent)
        } else {
            // 비회원 -> 로그인 다이얼로그
            showLoginDialog()
        }
    }

    private fun showLoginDialog() {
        val dialog = ConfirmDialog(
            "로그인이 필요해요!",
            "여행 일정을 추가/관리하고 싶다면\n로그인을 진행해주세요",
            "로그인하기",
        ) {
            val intent = Intent(context, IntroActivity::class.java).apply {
                putExtra("destination", IntroRoute.Login.route)
            }
            startActivity(intent)
            requireActivity().finish()
        }
        dialog.isCancelable = true
        dialog.show(activity?.supportFragmentManager!!, "ScheduleLoginDialog")
    }

    private fun initScheduleDetailActivity(planId: Long){
        val intent = Intent(requireActivity(), ScheduleDetailActivity::class.java)
        intent.putExtra("planId", planId)
        scheduleDetailLauncher.launch(intent)
    }

}