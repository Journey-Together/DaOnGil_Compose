package kr.techit.lion.presentation.schedule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kr.techit.lion.presentation.R
import kr.techit.lion.presentation.databinding.ActivityPublicScheduleBinding
import kr.techit.lion.presentation.delegate.NetworkState
import kr.techit.lion.presentation.ext.gridAddOnScrollEndListener
import kr.techit.lion.presentation.ext.repeatOnStarted
import kr.techit.lion.presentation.connectivity.ConnectivityObserver
import kr.techit.lion.presentation.connectivity.NetworkConnectivityObserver
import kr.techit.lion.presentation.schedule.adapter.PublicScheduleAdapter
import kr.techit.lion.presentation.schedule.vm.PublicScheduleViewModel

@AndroidEntryPoint
class PublicScheduleActivity : AppCompatActivity() {

    private val viewModel: PublicScheduleViewModel by viewModels()

    private val connectivityObserver: ConnectivityObserver by lazy {
        NetworkConnectivityObserver(applicationContext)
    }

    private val binding: ActivityPublicScheduleBinding by lazy {
        ActivityPublicScheduleBinding.inflate(layoutInflater)
    }

    private val scheduleAdapter: PublicScheduleAdapter by lazy {
        PublicScheduleAdapter { position ->
            // 공개 일정 상세보기 페이지로 이동
            val intent = Intent(this@PublicScheduleActivity, ScheduleDetailActivity::class.java)
            intent.putExtra("planId", scheduleAdapter.currentList[position].planId)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initToolbar()
        initPublicScheduleRecyclerView()
        scrollPublicSchedule()

        repeatOnStarted {
            launch { collectPublicScheduleState() }
            launch { observeConnectivity() }
        }
    }

    private suspend fun collectPublicScheduleState() {
        with(binding) {
            repeatOnStarted {
                viewModel.networkState.collect { networkState ->
                    when (networkState) {
                        is NetworkState.Loading -> {
                            publicScheduleProgressBar.visibility = View.VISIBLE
                            publicScheduleErrorLayout.visibility = View.GONE
                            recyclerViewPublicScheduleList.visibility = View.GONE
                        }

                        is NetworkState.Success -> {
                            publicScheduleProgressBar.visibility = View.GONE
                            publicScheduleErrorLayout.visibility = View.GONE
                            recyclerViewPublicScheduleList.visibility = View.VISIBLE
                        }

                        is NetworkState.Error -> {
                            publicScheduleProgressBar.visibility = View.GONE
                            publicScheduleErrorLayout.visibility = View.VISIBLE
                            recyclerViewPublicScheduleList.visibility = View.GONE
                            publicScheduleErrorMsg.text = networkState.msg
                        }
                    }
                }
            }
        }
    }

    private suspend fun observeConnectivity() {
        with(binding) {
            connectivityObserver.getFlow().collect { connectivity ->
                when (connectivity) {
                    ConnectivityObserver.Status.Available -> {
                        publicScheduleErrorLayout.visibility = View.GONE
                        recyclerViewPublicScheduleList.visibility = View.VISIBLE
                        viewModel.getOpenPlanList()
                    }

                    ConnectivityObserver.Status.Unavailable,
                    ConnectivityObserver.Status.Losing,
                    ConnectivityObserver.Status.Lost -> {
                        publicScheduleProgressBar.visibility = View.GONE
                        publicScheduleErrorLayout.visibility = View.VISIBLE
                        recyclerViewPublicScheduleList.visibility = View.GONE
                        val msg = "${getString(R.string.text_network_is_unavailable)}\n" +
                                "${getString(R.string.text_plz_check_network)} "
                        publicScheduleErrorMsg.text = msg
                    }
                }
            }
        }
    }

    private fun initToolbar() {
        binding.toolbarPublicSchedule.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initPublicScheduleRecyclerView() {

        binding.recyclerViewPublicScheduleList.adapter = scheduleAdapter

        viewModel.openPlanList.observe(this@PublicScheduleActivity) { newList ->
            val rvState =
                binding.recyclerViewPublicScheduleList.layoutManager?.onSaveInstanceState()
            scheduleAdapter.submitList(newList) {
                binding.recyclerViewPublicScheduleList.layoutManager?.onRestoreInstanceState(rvState)
            }
        }
    }

    private fun scrollPublicSchedule() {
        binding.recyclerViewPublicScheduleList.gridAddOnScrollEndListener {
            viewModel.getOpenPlanListPaging()
        }
    }
}