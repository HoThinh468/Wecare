package com.vn.wecare.feature.account.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.viewmodel.AccountViewModel
import com.vn.wecare.ui.theme.*
import com.vn.wecare.utils.common_composable.CustomTextField
import com.vn.wecare.utils.common_composable.LoadingDialog

@Composable
fun ReAuthenticateDialog(
    modifier: Modifier,
    onDismissDialog: () -> Unit,
    viewModel: AccountViewModel
) {

    val uiState = viewModel.changePasswordUiState.collectAsState()

    uiState.value.reAuthenticateResult.let {
        when (it) {
            is Response.Loading -> {
                LoadingDialog(loading = it == Response.Loading) {}
            }
            is Response.Success -> {
                viewModel.handleReAuthenticateSuccessful()
            }
            is Response.Error -> {
                Toast.makeText(
                    LocalContext.current,
                    "Wrong password, please try again!",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.clearReAutheticateResult()
            }
            else -> {/*do nothing*/
            }
        }
    }

    uiState.value.isChangePasswordDialogShow.let {
        if (it) {
            ChangePasswordDialog(
                modifier = modifier,
                onCloseDialogClick = {
                    viewModel.apply {
                        onDismissChangePasswordDialog()
                        onDismissReAuthenticateDialog()
                    }
                },
                viewModel = viewModel
            )
        }
    }

    Dialog(onDismissRequest = {
        onDismissDialog()
        viewModel.clearReAuthenticateInfo()
    }) {
        Surface(
            modifier = modifier
                .wrapContentHeight(unbounded = true)
                .fillMaxWidth(),
            shape = RoundedCornerShape(smallRadius)
        ) {
            Column(
                modifier = modifier.padding(vertical = midPadding, horizontal = mediumPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Please enter your password to continue the process",
                    style = MaterialTheme.typography.body1
                )
                CustomTextField(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = normalPadding),
                    hint = "Enter your password",
                    label = "Password",
                    backgroundColor = Color.White,
                    cursorColor = MaterialTheme.colors.primary,
                    leadingIcon = Icons.Filled.VpnKey,
                    onValueChange = viewModel::onPasswordChange,
                    trailingIcon = if (uiState.value.isPasswordShow) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    isTextVisible = uiState.value.isPasswordShow,
                    onShowTextClick = viewModel::onShowPasswordClick,
                    value = uiState.value.password,
                    isError = !uiState.value.isPasswordValid,
                    padding = 0.dp,
                    errorMessage = stringResource(
                        id = viewModel.getPasswordErrorMessage() ?: R.string.blank_text_id
                    )
                )
                Button(
                    modifier = modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(mediumRadius),
                    onClick = viewModel::onSubmitReAuthenticationPassword
                ) {
                    Text(text = "SUBMIT")
                }
                TextButton(onClick = {
                    onDismissDialog()
                    viewModel.clearReAuthenticateInfo()
                }) {
                    Text(text = "CLOSE", style = MaterialTheme.typography.button)
                }
            }
        }
    }
}