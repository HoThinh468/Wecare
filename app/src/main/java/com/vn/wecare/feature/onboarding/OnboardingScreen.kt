package com.vn.wecare.feature.onboarding

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.protobuf.Enum
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.goal.EnumGoal
import com.vn.wecare.feature.onboarding.composable.*
import com.vn.wecare.feature.onboarding.model.BMIState
import com.vn.wecare.feature.onboarding.viewmodel.OnboardingViewModel
import com.vn.wecare.utils.common_composable.LoadingDialog
import kotlinx.coroutines.launch

const val ONBOARDING_PAGE_COUNT = 5

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
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

    val sheetState =
        if (onboardingUiState.value.selectedGoal == EnumGoal.GAINMUSCLE || onboardingUiState.value.selectedGoal == EnumGoal.LOSEWEIGHT) {
            rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.HalfExpanded,
            )
        } else rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
        )

    ModalBottomSheetLayout(sheetContent = {
        DesiredWeightPickerBottomSheet(
            closeBottomSheet = {
                coroutineScope.launch { sheetState.hide() }
            },
            onDesiredWeightDifferencePickScrolled = {
                viewModel.onPickDesiredWeightDifferenceScroll(it)
            },
            estimatedTime = onboardingUiState.value.estimatedWeeks,
            desiredWeightDifference = onboardingUiState.value.desiredWeightDifferencePicker,
            goal = onboardingUiState.value.selectedGoal
        )
    }, sheetState = sheetState) {
        Scaffold(bottomBar = {
            OnboardingBottomNav(
                modifier = modifier,
                index = viewModel.currentIndex.value,
                onContinueClick = {
                    viewModel.onNextClick()
                },
                onPreviousClick = {
                    viewModel.onPreviousClick()
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(viewModel.currentIndex.value)
                    }
                },
            )
        }) {
            OnboardingScreenContent(
                modifier = modifier,
                pagerState = pagerState,
                viewModel = viewModel,
                sheetState = sheetState,
                coroutineScope = coroutineScope
            )
        }
    }
}