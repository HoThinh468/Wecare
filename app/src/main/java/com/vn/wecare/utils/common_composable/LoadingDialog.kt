package com.vn.wecare.utils.common_composable

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun LoadingDialog(loading: Boolean, onDismissRequest: () -> Unit) {
    if (loading) {
        Dialog(
            onDismissRequest = onDismissRequest,
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            CircularProgressIndicator()
        }
    }
}