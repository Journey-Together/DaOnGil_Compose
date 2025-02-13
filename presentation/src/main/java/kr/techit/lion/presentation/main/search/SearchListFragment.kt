package kr.techit.lion.presentation.main.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kr.techit.lion.presentation.R
import kr.techit.lion.presentation.databinding.FragmentSearchListBinding
import kr.techit.lion.presentation.delegate.NetworkEvent
import kr.techit.lion.presentation.delegate.NetworkState
import kr.techit.lion.presentation.ext.addOnScrollEndListener
import kr.techit.lion.presentation.ext.repeatOnViewStarted
import kr.techit.lion.presentation.home.DetailActivity
import kr.techit.lion.presentation.main.adapter.ListSearchAdapter
import kr.techit.lion.presentation.main.adapter.ListSearchAdapter.Companion.VIEW_TYPE_PLACE
import kr.techit.lion.presentation.main.search.vm.model.AreaModel
import kr.techit.lion.presentation.main.search.vm.model.DisabilityType
import kr.techit.lion.presentation.main.search.vm.SearchListViewModel

@AndroidEntryPoint
class SearchListFragment : Fragment(R.layout.fragment_search_list) {
    private val viewModel: SearchListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSearchListBinding.bind(view)

        val mainAdapter = ListSearchAdapter(
            viewLifecycleOwner.lifecycleScope,
            onClickPhysicalDisability = { type ->
            },
            onClickVisualImpairment = { type ->
            },
            onClickHearingDisability = { type ->
            },
            onClickInfantFamily = { type ->
            },
            onClickElderlyPeople = { type ->
            },
            onSelectArea = {
                repeatOnViewStarted {
                    viewModel.onSelectedArea(it)
                }
            },
            onSelectSigungu = {
                viewModel.onSelectedSigungu(it)
            },
            onSelectPlace = {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra("detailPlaceId", it)
                startActivity(intent)
            }
        )

        val rvLayoutManager = GridLayoutManager(requireContext(), 2)
        rvLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (mainAdapter.getItemViewType(position)) {
                    VIEW_TYPE_PLACE -> 1
                    else -> 2
                }
            }
        }

        val placeViewPool = RecyclerView.RecycledViewPool().apply {
            setMaxRecycledViews(VIEW_TYPE_PLACE, 10)
        }

        with(binding.rvSearchResult) {
            adapter = mainAdapter
            itemAnimator = null
            layoutManager = rvLayoutManager

            setRecycledViewPool(placeViewPool)
            addOnScrollEndListener {
                val pageState = viewModel.isLastPage.value
                if (pageState.not()) {
                    viewModel.whenLastPageReached()
                }
            }
        }

        with(binding) {
            repeatOnViewStarted {


                launch {
                    viewModel.networkEvent.collect { event ->
                        when (event) {
                            is NetworkEvent.Loading -> {
                                searchListProgressBar.visibility = View.VISIBLE
                            }

                            is NetworkEvent.Success -> {
                                searchListProgressBar.visibility = View.GONE
                                rvSearchResult.visibility = View.VISIBLE
                                noSearchResultContainer.visibility = View.GONE
                                searchListProgressBar.visibility = View.GONE
                            }

                            is NetworkEvent.Error -> {
                                searchListProgressBar.visibility = View.GONE
                                rvSearchResult.visibility = View.GONE
                                noSearchResultContainer.visibility = View.VISIBLE
                                textMsg.text = event.msg
                            }
                        }
                    }
                }

                launch {
                    viewModel.uiState
                        .filter { uiState ->
                            uiState.any { it is AreaModel && it.areas.isNotEmpty() }
                        }.collect {
                            mainAdapter.submitList(it)
                        }
                }

            }
        }
    }

    fun mapChanged(state: Boolean) {
        viewModel.onMapChanged(state)
    }

}
