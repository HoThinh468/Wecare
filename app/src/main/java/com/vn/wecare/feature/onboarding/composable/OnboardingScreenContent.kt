package com.vn.wecare.feature.onboarding.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.feature.onboarding.ONBOARDING_PAGE_COUNT
import com.vn.wecare.feature.onboarding.composable.dialog.OnboardingRecommendationDialog
import com.vn.wecare.feature.onboarding.composable.dialog.OnboardingWarningDialog
import com.vn.wecare.feature.onboarding.viewmodel.OnboardingViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun OnboardingScreenContent(
    modifier: Modifier,
    pagerState: PagerState,
    viewModel: OnboardingViewModel,
    sheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope
) {

    val uiState = viewModel.onboardingUiState.collectAsState().value
    val dialogUiState = viewModel.onboardingDialogUiState.collectAsState().value

    if (dialogUiState.shouldShowWarningDialog) {
        OnboardingWarningDialog(
            modifier = modifier,
            onDismissDialog = { viewModel.dismissWarningDialog() },
            title = dialogUiState.warningDialogTitle,
            message = dialogUiState.warningDialogMessage
        )
    }

    if (dialogUiState.shouldShowRecommendationDialog) {
        OnboardingRecommendationDialog(modifier = modifier,
            onDismissDialog = { viewModel.dismissRecommendationDialog() },
            title = dialogUiState.recommendationDialogTitle,
            message = dialogUiState.recommendationDialogMessage,
            onWishToProcessClick = { viewModel.onWishToProcessAfterChoosingGoalClick() })
    }

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
                        OnboardingGenderSelection(
                            modifier = modifier,
                            onGenderSelect = viewModel::onGenderSelect,
                            selectedGender = uiState.genderSelectionId
                        )
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
                        OnboardingHeightPicker(
                            modifier = modifier,
                            heightPicker = uiState.heightPicker,
                            onPickHeightScroll = viewModel::onPickHeightScroll
                        )
                    }
                }

                3 -> {
                    BaseOnboardingContent(
                        modifier = modifier,
                        titleRes = R.string.weight_picker_title,
                        subtitleRes = R.string.weight_picker_subtitle
                    ) {
                        OnboardingWeightPicker(
                            modifier = modifier,
                            weightPicked = uiState.weightPicker,
                            onPickWeightScrolled = viewModel::onPickWeightScroll,
                        )
                    }
                }

                else -> {
                    BaseOnboardingContent(
                        modifier = modifier,
                        titleRes = R.string.goal_selection_title,
                        subtitleRes = R.string.goal_selection_subtitle
                    ) {
                        OnboardingGoalSelection(
                            modifier = modifier,
                            onGoalSelect = viewModel::onGoalSelect,
                            goalSelectedId = uiState.selectedGoal,
                            openDesiredWeightPickerBottomSheet = {
                                coroutineScope.launch {
                                    if (sheetState.isVisible) {
                                        sheetState.hide()
                                    } else {
                                        sheetState.show()
                                    }
                                }
                            },
                        )
                    }
                }
            }
        }
    }
}