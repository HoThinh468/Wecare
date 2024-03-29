package com.vn.wecare.feature.food.addmeal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vn.wecare.ui.theme.smallPadding

@Composable
fun NutrientIndexItem(modifier: Modifier, color: Color, title: String, index: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = modifier
                .size(12.dp)
                .background(color = color, shape = CircleShape)
        )
        Column(modifier = modifier.padding(start = smallPadding)) {
            Text(text = title, style = MaterialTheme.typography.body2)
            Text(
                text = index, style = MaterialTheme.typography.caption
            )
        }
    }
}