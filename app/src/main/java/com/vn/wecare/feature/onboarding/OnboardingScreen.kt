package com.vn.wecare.feature.onboarding

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.onboarding.composable.*
import com.vn.wecare.feature.onboarding.viewmodel.OnboardingViewModel
import com.vn.wecare.ui.theme.mediumRadius
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.utils.common_composable.LoadingDialog
import kotlinx.coroutines.launch

const val ONBOARDING_PAGE_COUNT = 5

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier, viewModel: OnboardingViewModel, moveToSplashScreen: () -> Unit
) {

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val onboardingUiState = viewModel.onboardingUiState.collectAsState()

    onboardingUiState.value.updateInformationResult.let {
        when (it) {
            is Response.Loading -> LoadingDialog(loading = it == Response.Loading) {}
            is Response.Success -> {
                viewModel.moveToNextOnboardingPage(
                    moveToSplashScreen
                ) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(viewModel.currentIndex.value)
                    }
                }
            }
            is Response.Error -> {
                Toast.makeText(
                    LocalContext.current, "Cannot sync data, please try later", Toast.LENGTH_SHORT
                ).show()
            }
            else -> { /* do nothing */
            }
        }
    }

    Scaffold(bottomBar = {
        OnboardingBottomNav(modifier = modifier,
            index = viewModel.currentIndex.value,
            onContinueClick = {
                viewModel.onNextClick()
            },
            onPreviousClick = {
                viewModel.onPreviousClick()
                coroutineScope.launch {
                    pagerState.animateScrollToPage(viewModel.currentIndex.value)
                }
            })
    }) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .width(176.dp)
                    .height(100.dp)
                    .padding(top = 32.dp),
                painter = painterResource(R.drawable.logo2),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            HorizontalPager(
                pageCount = ONBOARDING_PAGE_COUNT, userScrollEnabled = false, state = pagerState
            ) {
                when (it) {
                    0 -> {
                        BaseOnboardingContent(
                            modifier = modifier,
                            titleRes = R.string.gender_selection_title,
                            subtitleRes = R.string.gender_selection_subtitle
                        ) {
                            OnboardingGenderSelection(modifier = modifier, viewModel = viewModel)
                        }
                    }
                    1 -> {
                        BaseOnboardingContent(
                            modifier = modifier,
                            titleRes = R.string.age_picker_title,
                            subtitleRes = R.string.age_picker_subtitle
                        ) {
                            OnboardingAgePicker(modifier = modifier, viewModel = viewModel)
                        }
                    }
                    2 -> {
                        BaseOnboardingContent(
                            modifier = modifier,
                            titleRes = R.string.height_picker_title,
                            subtitleRes = R.string.height_picker_subtitle
                        ) {
                            OnboardingHeightPicker(modifier = modifier, viewModel = viewModel)
                        }
                    }
                    3 -> {
                        BaseOnboardingContent(
                            modifier = modifier,
                            titleRes = R.string.weight_picker_title,
                            subtitleRes = R.string.weight_picker_subtitle
                        ) {
                            OnboardingWeightPicker(modifier = modifier, viewModel = viewModel)
                        }
                    }
                    else -> {
                        BaseOnboardingContent(
                            modifier = modifier,
                            titleRes = R.string.goal_selection_title,
                            subtitleRes = R.string.goal_selection_subtitle
                        ) {
                            OnboardingGoalSelection(modifier = modifier, viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OnboardingBottomNav(
    modifier: Modifier, index: Int, onContinueClick: () -> Unit, onPreviousClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier
                .height(40.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            repeat(ONBOARDING_PAGE_COUNT) { iteration ->
                val color =
                    if (index == iteration) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
                val width = if (index == iteration) 20.dp else 10.dp
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .height(10.dp)
                        .width(width)
                )
            }
        }
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(90.dp),
            backgroundColor = MaterialTheme.colors.primary,
            elevation = 0.dp,
            shape = RoundedCornerShape(
                topStart = mediumRadius,
                topEnd = mediumRadius,
            )
        ) {
            Row(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = midPadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = modifier.clickable { onPreviousClick() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (index > 0) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_double_arrow),
                            contentDescription = null,
                            tint = MaterialTheme.colors.onPrimary
                        )
                        Text(
                            text = "Previous",
                            style = MaterialTheme.typography.button,
                            color = MaterialTheme.colors.onPrimary
                        )
                    }
                }
                Button(
                    onClick = onContinueClick,
                    shape = RoundedCornerShape(mediumRadius),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onPrimary)
                ) {
                    Text(
                        text = if (index == 4) "Finish" else "Continue",
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
        }
    }
}