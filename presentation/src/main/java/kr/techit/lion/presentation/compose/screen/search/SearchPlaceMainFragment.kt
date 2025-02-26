package kr.techit.lion.presentation.compose.screen.search

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.techit.lion.presentation.R
import kr.techit.lion.presentation.main.search.vm.SearchViewModel

@AndroidEntryPoint
class SearchPlaceMainFragment : Fragment(R.layout.fragment_search_place_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val composeView = view.findViewById<ComposeView>(R.id.compose_view)
        composeView.setContent {
            SearchPlaceMainScreen()
        }

//        if (requireContext().isTallBackEnabled()){
//            viewLifecycleOwner.lifecycleScope.launch {
//                delay(3000)
//                requireContext().announceForAccessibility(
//                    getString(R.string.text_this_is_place_search)
//                            + getString(R.string.text_script_read_all_text)
//                )
//            }
//            with(binding){
//                readScriptBtn.visibility = View.VISIBLE
//                readScriptBtn.setOnClickListener {
//                    requireContext().announceForAccessibility(
//                        getString(R.string.text_script_guide_for_place_search)
//                    )
//                }
//                searchButton.accessibilityDelegate = object : View.AccessibilityDelegate(){
//                    override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfo) {
//                        super.onInitializeAccessibilityNodeInfo(host, info)
//                        info.hintText = null
//                        info.contentDescription = "관광지텍스트검색"
//                    }
//                }
//            }
//        }
//
//        childFragmentManager.beginTransaction().apply {
//            add(R.id.fragmentContainerView, listFragment, ScreenState.List.name)
//            add(R.id.fragmentContainerView, mapFragment, ScreenState.Map.name)
//            hide(mapFragment)
//            commit()
//        }
//
//        with(binding) {
//            searchButton.setOnClickListener {
//                startActivity(Intent(requireContext(), KeywordSearchActivity::class.java))
//            }
//
//            tabContainer.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//                override fun onTabSelected(tab: TabLayout.Tab) {
//                    // 탭이 선택되었을 때 수행할 작업
//                    when (tab.position) {
//                        0 -> sharedViewModel.onTabChanged(Category.PLACE)
//                        1 -> sharedViewModel.onTabChanged(Category.RESTAURANT)
//                        2 -> sharedViewModel.onTabChanged(Category.ROOM)
//                    }
//                }
//
//                override fun onTabUnselected(tab: TabLayout.Tab) {}
//
//                override fun onTabReselected(tab: TabLayout.Tab) {}
//            })
//
//            modeSwitchBtn.setOnClickListener {
//                when (viewModel.screenState.value) {
//                    ScreenState.List -> viewModel.changeScreenState(ScreenState.Map)
//                    ScreenState.Map -> viewModel.changeScreenState(ScreenState.List)
//                }
//            }
//
//            repeatOnViewStarted {
//                viewModel.screenState.collect {
//                    when (it) {
//                        ScreenState.Map -> {
//                            showFragment(mapFragment)
//                            hideFragment(listFragment)
//                            modeSwitchBtn.setText(R.string.watching_list)
//                            modeSwitchBtn.setIconResource(R.drawable.list_icon)
//                        }
//
//                        ScreenState.List -> {
//                            showFragment(listFragment)
//                            hideFragment(mapFragment)
//                            modeSwitchBtn.setText(R.string.watching_map)
//                            modeSwitchBtn.setIconResource(R.drawable.map_icon)
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private fun showFragment(fragment: Fragment) {
//        if (fragment is SearchListFragment) {
//            if (viewModel.firstScreen.value > 0) {
//                fragment.mapChanged(true)
//            }
//        }
//        childFragmentManager.beginTransaction().apply {
//            show(fragment)
//            commit()
//        }
//    }
//
//    private fun hideFragment(fragment: Fragment) {
//        childFragmentManager.beginTransaction().apply {
//            hide(fragment)
//            commit()
//        }
//    }
    }
}