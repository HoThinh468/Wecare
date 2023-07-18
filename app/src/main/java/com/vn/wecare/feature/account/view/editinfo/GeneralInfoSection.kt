package com.vn.wecare.feature.account.view.editinfo

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Scale
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.vn.wecare.feature.account.viewmodel.EditInfoViewModel
import com.vn.wecare.feature.onboarding.composable.OnboardingGenderSelection
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.utils.WecareUserConstantValues.MAX_AGE
import com.vn.wecare.utils.WecareUserConstantValues.MAX_HEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MAX_WEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MIN_AGE
import com.vn.wecare.utils.WecareUserConstantValues.MIN_HEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MIN_WEIGHT

@Composable
fun GeneralInformation(
    modifier: Modifier, viewModel: EditInfoViewModel
) {

    val uiState = viewModel.editInfoUiState.collectAsState().value
    val editGoalUiState = viewModel.editGoalInfoUiState.collectAsState().value

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
        isError = !uiState.isHeightValid,
        enabled = editGoalUiState.isGoalExpired
    )
    if (!uiState.isHeightValid) {
        Spacer(modifier = modifier.height(smallPadding))
        Text(
            text = "*Your height must between $MIN_HEIGHT and $MAX_HEIGHT cm",
            style = MaterialTheme.typography.caption.copy(color = Red400)
        )
    }
    if (!editGoalUiState.isGoalExpired) {
        Spacer(modifier = modifier.height(smallPadding))
        Text(
            text = "*Complete your current goal to edit this field",
            style = MaterialTheme.typography.caption
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
        isError = !uiState.isWeightValid,
        enabled = editGoalUiState.isGoalExpired
    )
    if (!uiState.isWeightValid) {
        Spacer(modifier = modifier.height(smallPadding))
        Text(
            text = "*Your weight must be between $MIN_WEIGHT and $MAX_WEIGHT kg",
            style = MaterialTheme.typography.caption.copy(color = Red400)
        )
    }
    if (!editGoalUiState.isGoalExpired) {
        Spacer(modifier = modifier.height(smallPadding))
        Text(
            text = "*Complete your current goal to edit this field",
            style = MaterialTheme.typography.caption
        )
    }
    Spacer(modifier = modifier.height(normalPadding))
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
    Spacer(modifier = modifier.height(normalPadding))
    Text(
        modifier = modifier.padding(bottom = halfMidPadding),
        text = "Gender",
        style = MaterialTheme.typography.body1
    )
    OnboardingGenderSelection(
        modifier = modifier,
        onGenderSelect = viewModel::onGenderSelected,
        selectedGender = uiState.currentChosenGender
    )
}