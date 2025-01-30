package kr.techit.lion.presentation.compose.screen.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kr.techit.lion.presentation.R
import kr.techit.lion.presentation.compose.ext.clickableWithCustomRippleEffect

@Composable
fun OnboardingScreen(
    pages: List<OnBoardingPage>,
    navigateToLogin: () -> Unit,
    navigateToMain: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        pageCount = { pages.size }
    )
    val endPage = pages.size - 1

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        HorizontalPager(state = pagerState) { idx ->
            val page = pages[idx]
            OnBoardingPageView(page)
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(100.dp))

        Button(
            onClick = {
                if (pagerState.currentPage == endPage) {
                    navigateToMain()
                } else {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            },
            modifier = Modifier
                .clickableWithCustomRippleEffect {
                    if (pagerState.currentPage == endPage) {
                        navigateToLogin()
                    } else {
                        coroutineScope.launch {
                            pagerState.scrollToPage(endPage)
                        }
                    }
                }
                .fillMaxWidth()
                .height(58.dp)
                .padding(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Yellow
            ),
            content = {
                Text(
                    text = if (pagerState.currentPage == endPage) {
                        stringResource(R.string.text_start_main)
                    } else {
                        stringResource(R.string.text_next)
                    },
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            },
        )

        Text(
            text = if (pagerState.currentPage == endPage) {
                stringResource(R.string.text_login_sign)
            } else {
                stringResource(R.string.text_skip)
            },
            modifier = Modifier.clickable {
                if (pagerState.currentPage == endPage) {
                    navigateToLogin()
                } else {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(endPage)
                    }
                }
            },
            style = if (pagerState.currentPage != endPage){
                TextStyle(textDecoration = TextDecoration.Underline)
            } else{
                TextStyle(textDecoration = TextDecoration.None)
            }
        )
    }
}

@Composable
fun OnBoardingPageView(page: OnBoardingPage) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = page.imageId),
            contentDescription = null,
        )

        Text(
            text = page.text1,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = page.text2,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 5.dp)
        )

        page.text3?.let {
            Text(text = it, fontSize = 20.sp)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun OnboardingScreenPreview() {
    OnboardingScreen(
        pages = listOf(
            OnBoardingPage(
                R.drawable.onboarding_first,
                "여행의 모든 문턱을 낮추다",
                "다온길에서 무장애 여행 정보를 확인하세요"
            ),
            OnBoardingPage(
                R.drawable.onboarding_second,
                "안전한 여행의 첫 걸음",
                "언제 어디서든 응급 지원정보를 확인하세요"
            ),
            OnBoardingPage(
                R.drawable.onboarding_third,
                "한곳에서 완성되는 여행",
                "여행 계획부터 후기 공유까지 한번에 관리해요"
            ),
            OnBoardingPage(
                R.drawable.onboarding_last,
                "행복이 다가온 여행길, 다온길",
                "     모두를 위한 행운 이정표",
                "누구든 떠날 자유, 모두가 누릴 행복"
            )
        ),
        navigateToLogin = {},
        navigateToMain = {}
    )
}