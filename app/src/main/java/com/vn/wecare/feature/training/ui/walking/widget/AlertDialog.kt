package com.vn.wecare.feature.training.ui.walking.widget

import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.vn.wecare.R
import androidx.compose.material.AlertDialog
import androidx.compose.runtime.*

@Composable
fun CustomAlertDialog (
    modifier: Modifier = Modifier,
    textId: Int,
    onConfirmClick: () -> Unit,
    isOpen: Boolean
) {
    var openDialog by remember { mutableStateOf(isOpen) }
    //openDialog = isOpen

    if (openDialog) {
        AlertDialog(
            onDismissRequest = { openDialog = false },
            title = {
                Text(
                    text = stringResource(id = textId),
                    style = MaterialTheme.typography.h3
                )
            },
            confirmButton = {
                Button(onClick = onConfirmClick) {
                    Text(stringResource(id = R.string.button_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { openDialog = false }) {
                    Text(stringResource(id = R.string.button_cancel))
                }
            }
        )
    }
}