package com.vn.wecare.feature.food.addyourownmeal.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vn.wecare.ui.theme.normalPadding

@Composable
fun NameAndDescriptionSection(
    modifier: Modifier
) {
    Text("Name", style = MaterialTheme.typography.body1)
    OutlinedTextField(modifier = modifier.fillMaxWidth(),
        value = "",
        onValueChange = { },
        label = { Text("Enter meal name") },
        maxLines = 1,
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        },
        isError = false
    )
    Spacer(modifier = modifier.height(normalPadding))
    Text("Description", style = MaterialTheme.typography.body1)
    OutlinedTextField(modifier = modifier.fillMaxWidth(),
        value = "",
        onValueChange = { },
        label = { Text("Enter description") },
        maxLines = 3,
        minLines = 2,
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        },
        keyboardActions = KeyboardActions(onDone = {})
    )
}