package com.vn.wecare.feature.account.view

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Scale
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.viewmodel.EditInfoViewModel
import com.vn.wecare.feature.onboarding.composable.RoundedIconButton
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.ui.theme.xxxExtraPadding
import com.vn.wecare.utils.WecareUserConstantValues
import com.vn.wecare.utils.WecareUserConstantValues.MAX_AGE
import com.vn.wecare.utils.WecareUserConstantValues.MAX_HEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MAX_WEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MIN_AGE
import com.vn.wecare.utils.WecareUserConstantValues.MIN_HEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MIN_WEIGHT
import com.vn.wecare.utils.common_composable.IconButtonWithFullWidth
import com.vn.wecare.utils.common_composable.LoadingDialog
import com.vn.wecare.utils.common_composable.WecareAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditInfoScreen(
    modifier: Modifier = Modifier, navigateBack: () -> Unit, viewModel: EditInfoViewModel
) {

    val focusManager = LocalFocusManager.current
    val uiState = viewModel.editInfoUiState.collectAsState().value

    val context = LocalContext.current

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
            }

            is Response.Error -> {
                Toast.makeText(context, "Update fail!", Toast.LENGTH_SHORT).show()
            }

            else -> { /* Do nothing */
            }
        }
    }

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
            PersonalInformation(modifier = modifier, viewModel = viewModel)
            Spacer(modifier = modifier.height(normalPadding))
            PickGoalSection(
                modifier = modifier,
                currentChosenGoalId = uiState.currentChosenGoal,
                onGoalSelected = viewModel::onGoalSelected
            )
            Spacer(modifier = modifier.height(normalPadding))
            SelectYourGender(
                modifier = modifier,
                currentChosenGenderId = uiState.currentChosenGender,
                onGenderSelected = viewModel::onGenderSelected
            )
            Spacer(modifier = modifier.height(xxxExtraPadding))
        }
    }
}

@Composable
private fun PersonalInformation(
    modifier: Modifier, viewModel: EditInfoViewModel
) {

    val uiState = viewModel.editInfoUiState.collectAsState().value

    Text("Username", style = MaterialTheme.typography.body1)
    OutlinedTextField(modifier = modifier.fillMaxWidth(),
        value = viewModel.userName,
        onValueChange = {
            viewModel.onUserNameChange(it)
        },
        label = { Text("Enter username") },
        maxLines = 1,
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Badge, contentDescription = null)
        },
        trailingIcon = {
            IconButton(onClick = {
                viewModel.clearUserName()
            }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        },
        isError = !uiState.isUserNameValid
    )
    if (!uiState.isUserNameValid) {
        Spacer(modifier = modifier.height(smallPadding))
        Text(
            text = "*Username is invalid",
            style = MaterialTheme.typography.caption.copy(color = Red400)
        )
    }
    Spacer(modifier = modifier.height(normalPadding))
    Text("Height", style = MaterialTheme.typography.body1)
    OutlinedTextField(modifier = modifier.fillMaxWidth(),
        value = viewModel.height,
        onValueChange = {
            viewModel.onHeightChange(it)
        },
        label = { Text("Enter height") },
        maxLines = 1,
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Height, contentDescription = null)
        },
        trailingIcon = {
            IconButton(onClick = {
                viewModel.clearHeight()
            }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
        isError = !uiState.isHeightValid
    )
    if (!uiState.isHeightValid) {
        Spacer(modifier = modifier.height(smallPadding))
        Text(
            text = "*Your height must between $MIN_HEIGHT and $MAX_HEIGHT cm",
            style = MaterialTheme.typography.caption.copy(color = Red400)
        )
    }
    Spacer(modifier = modifier.height(normalPadding))
    Text("Weight", style = MaterialTheme.typography.body1)
    OutlinedTextField(modifier = modifier.fillMaxWidth(),
        value = viewModel.weight,
        onValueChange = {
            viewModel.onWeightChange(it)
        },
        label = { Text("Enter weight") },
        maxLines = 1,
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Scale, contentDescription = null)
        },
        trailingIcon = {
            IconButton(onClick = { viewModel.clearWeight() }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
        isError = !uiState.isWeightValid
    )
    if (!uiState.isWeightValid) {
        Spacer(modifier = modifier.height(smallPadding))
        Text(
            text = "*Your weight must be between $MIN_WEIGHT and $MAX_WEIGHT kg",
            style = MaterialTheme.typography.caption.copy(color = Red400)
        )
    }
    Text("Age", style = MaterialTheme.typography.body1)
    OutlinedTextField(modifier = modifier.fillMaxWidth(),
        value = viewModel.age,
        onValueChange = {
            viewModel.onAgeChange(it)
        },
        label = { Text("Enter age") },
        maxLines = 1,
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Person, contentDescription = null)
        },
        trailingIcon = {
            IconButton(onClick = { viewModel.clearAge() }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
        isError = !uiState.isAgeValid
    )
    if (!uiState.isAgeValid) {
        Spacer(modifier = modifier.height(smallPadding))
        Text(
            text = "*Your age must be between $MIN_AGE and $MAX_AGE y/o",
            style = MaterialTheme.typography.caption.copy(color = Red400)
        )
    }
}

@Composable
private fun PickGoalSection(
    modifier: Modifier, currentChosenGoalId: Int, onGoalSelected: (id: Int) -> Unit
) {
    Text(
        modifier = modifier.padding(bottom = halfMidPadding),
        text = "Pick a goal",
        style = MaterialTheme.typography.body1
    )
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        RoundedIconButton(
            modifier = modifier,
            iconRes = R.drawable.ic_muscle,
            buttonText = WecareUserConstantValues.GAIN_MUSCLE,
            bgColor = getBgColor(currentID = 1, givenID = currentChosenGoalId),
            contentColor = getContentColor(currentID = 1, givenID = currentChosenGoalId)
        ) { onGoalSelected(1) }
        Spacer(modifier = modifier.width(normalPadding))
        RoundedIconButton(
            modifier = modifier,
            iconRes = R.drawable.ic_weight,
            buttonText = WecareUserConstantValues.LOOSE_WEIGHT,
            bgColor = getBgColor(currentID = 2, givenID = currentChosenGoalId),
            contentColor = getContentColor(currentID = 2, givenID = currentChosenGoalId)
        ) { onGoalSelected(2) }
    }
    Spacer(modifier = modifier.height(halfMidPadding))
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        RoundedIconButton(
            modifier = modifier,
            iconRes = R.drawable.ic_heart,
            buttonText = WecareUserConstantValues.GET_HEALTHIER,
            bgColor = getBgColor(currentID = 3, givenID = currentChosenGoalId),
            contentColor = getContentColor(currentID = 3, givenID = currentChosenGoalId)
        ) { onGoalSelected(3) }
        Spacer(modifier = modifier.height(normalPadding))
        RoundedIconButton(
            modifier = modifier,
            iconRes = R.drawable.ic_mood_happy,
            buttonText = WecareUserConstantValues.IMPROVE_MOOD,
            bgColor = getBgColor(currentID = 4, givenID = currentChosenGoalId),
            contentColor = getContentColor(currentID = 4, givenID = currentChosenGoalId)
        ) { onGoalSelected(4) }
    }
}

@Composable
private fun SelectYourGender(
    modifier: Modifier, currentChosenGenderId: Int, onGenderSelected: (id: Int) -> Unit
) {
    Text(
        modifier = modifier.padding(bottom = halfMidPadding),
        text = "Select your gender",
        style = MaterialTheme.typography.body1
    )
    IconButtonWithFullWidth(
        buttonText = "Male",
        iconVector = Icons.Filled.Male,
        buttonColor = getBgColor(currentID = 1, givenID = currentChosenGenderId),
        contentColor = getContentColor(currentID = 1, givenID = currentChosenGenderId)
    ) {
        onGenderSelected(1)
    }
    Spacer(modifier = modifier.height(halfMidPadding))
    IconButtonWithFullWidth(
        buttonText = "Female",
        iconVector = Icons.Filled.Female,
        buttonColor = getBgColor(currentID = 2, givenID = currentChosenGenderId),
        contentColor = getContentColor(currentID = 2, givenID = currentChosenGenderId)
    ) {
        onGenderSelected(2)
    }
}

@Composable
private fun getBgColor(currentID: Int, givenID: Int): Color {
    return if (currentID == givenID) MaterialTheme.colors.primary else MaterialTheme.colors.onPrimary
}

@Composable
private fun getContentColor(currentID: Int, givenID: Int): Color {
    return if (currentID == givenID) MaterialTheme.colors.onPrimary else MaterialTheme.colors.primary
}