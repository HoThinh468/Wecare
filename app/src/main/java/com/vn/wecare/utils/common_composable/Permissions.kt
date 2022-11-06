package com.vn.wecare.utils.common_composable

import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.vn.wecare.R

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermission(
    permission: String,
    rationaleMessageRes: Int = R.string.rationale_message
) {

    val permissionState = rememberPermissionState(permission)

    if (!permissionState.hasPermission) {
        RequestPermissionDialog(
            rationaleMessageRes = rationaleMessageRes,
            requestPermission = { permissionState.launchPermissionRequest() }
        )
    }
}

@ExperimentalPermissionsApi
@Composable
private fun RequestPermissionDialog(
    @StringRes rationaleMessageRes: Int,
    requestPermission: () -> Unit
) {

    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = {
                Text(
                    text = stringResource(id = R.string.permission_dialog_title),
                    style = MaterialTheme.typography.h3
                )
            },
            text = {
                Text(stringResource(id = rationaleMessageRes))
            },
            confirmButton = {
                Button(onClick = requestPermission) {
                    Text(stringResource(id = R.string.give_permission_button_title))
                }
            },
            dismissButton = {
                TextButton(onClick = { openDialog.value = false }) {
                    Text(stringResource(id = R.string.close_dialog_title))
                }
            }
        )
    }
}