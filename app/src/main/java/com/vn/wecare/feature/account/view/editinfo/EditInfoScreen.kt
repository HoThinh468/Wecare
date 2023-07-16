package com.vn.wecare.feature.account.view.editinfo

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.viewmodel.EditInfoViewModel
import com.vn.wecare.feature.onboarding.composable.DesiredWeightPickerBottomSheet
import com.vn.wecare.feature.onboarding.composable.dialog.OnboardingRecommendationDialog
import com.vn.wecare.feature.onboarding.composable.dialog.OnboardingWarningDialog
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.ui.theme.xxxExtraPadding
import com.vn.wecare.utils.common_composable.LoadingDialog
import com.vn.wecare.utils.common_composable.WecareAppBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditInfoScreen(
    modifier: Modifier = Modifier, navigateBack: () -> Unit, viewModel: EditInfoViewModel
) {

    val focusManager = LocalFocusManager.current
    val uiState = viewModel.editInfoUiState.collectAsState().value
    val editGoalUiState = viewModel.editGoalInfoUiState.collectAsState().value

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val openWarningDialog = remember {
        mutableStateOf(false)
    }

    if (openWarningDialog.value) {
        AlertDialog(onDismissRequest = {
            openWarningDialog.value = false
        }, title = {
            Text(text = "Are you sure", style = MaterialTheme.typography.h5)
        }, text = {
            Text(
                text = "You didn't save your information, if you leave it won't be saved!",
                style = MaterialTheme.typography.body2
            )
        }, buttons = {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(smallPadding),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = {
                    openWarningDialog.value = false
                    navigateBack()
                }) {
                    Text("Leave", style = MaterialTheme.typography.button)
                }
                Button(onClick = { openWarningDialog.value = false }) {
                    Text(text = "Stay", style = MaterialTheme.typography.button)
                }
            }
        })
    }

    uiState.updateInfoResult.let {
        when (it) {
            is Response.Loading -> {
                LoadingDialog(loading = it == Response.Loading) {}
            }

            is Response.Success -> {
                Toast.makeText(context, "Update successfully!", Toast.LENGTH_SHORT).show()
                viewModel.resetUpdateRes()
                navigateBack()
            }

            is Response.Error -> {
                Toast.makeText(context, "Update fail!", Toast.LENGTH_SHORT).show()
                viewModel.resetUpdateRes()
            }

            else -> { /* Do nothing */
            }
        }
    }

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
            onWishToProcessClick = { viewModel.saveNewUserInfoAndNewGoal() })
    }

    ModalBottomSheetLayout(sheetContent = {
        DesiredWeightPickerBottomSheet(
            closeBottomSheet = {
                coroutineScope.launch { sheetState.hide() }
            },
            onDesiredWeightDifferencePickScrolled = viewModel::onPickDesiredWeightDifferenceScroll,
            desiredWeightDifference = editGoalUiState.desiredWeightDifferencePicker,
        )
    }, sheetState = sheetState) {
        Scaffold(
            modifier = modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            focusManager.clearFocus()
                        },
                    )
                },
            backgroundColor = MaterialTheme.colors.background,
            topBar = {
                WecareAppBar(
                    leadingIconRes = R.drawable.ic_close,
                    modifier = modifier,
                    onLeadingIconPress = {
                        viewModel.checkIfNewInfoIsDifferent()
                        if (!uiState.isNewInfoDifferent) {
                            navigateBack()
                        } else {
                            openWarningDialog.value = true
                        }
                    },
                    title = "Edit information",
                    trailingIconRes = R.drawable.ic_done_all,
                    onTrailingIconPress = {
                        viewModel.onSaveInfoClick(showToast = {
                            Toast.makeText(
                                context,
                                "Please update your information before save!",
                                Toast.LENGTH_SHORT
                            ).show()
                        })
                    },
                )
            },
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = midPadding)
                    .verticalScroll(
                        rememberScrollState()
                    )
            ) {
                Spacer(modifier = modifier.height(normalPadding))
                GeneralInformation(modifier = modifier, viewModel = viewModel)
                Spacer(modifier = modifier.height(normalPadding))
                EditInfoGoalSelectionSection(modifier = modifier, viewModel = viewModel) {
                    coroutineScope.launch {
                        if (sheetState.isVisible) {
                            sheetState.hide()
                        } else {
                            sheetState.show()
                        }
                    }
                }
                Spacer(modifier = modifier.height(xxxExtraPadding))
            }
        }
    }
}