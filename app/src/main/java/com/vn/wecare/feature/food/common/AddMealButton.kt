package com.vn.wecare.feature.food.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.smallPadding

@Composable
fun AddMealButton(
    modifier: Modifier,
    isEnable: Boolean = true,
    onButtonClick: () -> Unit
) {
    Button(
        modifier = modifier
            .padding(midPadding)
            .fillMaxWidth()
            .height(40.dp),
        onClick = { onButtonClick() },
        shape = Shapes.large,
        enabled = isEnable
    ) {
        Icon(
            modifier = modifier
                .padding(end = smallPadding)
                .size(16.dp),
            imageVector = Icons.Default.Add,
            contentDescription = null
        )
        Text(text = "Add meal", style = MaterialTheme.typography.button)
    }
}