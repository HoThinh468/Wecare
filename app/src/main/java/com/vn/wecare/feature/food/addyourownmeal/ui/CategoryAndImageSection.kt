package com.vn.wecare.feature.food.addyourownmeal.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BreakfastDining
import androidx.compose.material.icons.filled.BrunchDining
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding

@Composable
fun CategoryAndImageSection(
    modifier: Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        CategorySection(modifier)
    }
}

@Composable
private fun CategorySection(modifier: Modifier) {
    Column() {
        Text("Category", style = MaterialTheme.typography.body1)
        Spacer(modifier = modifier.height(smallPadding))
        MealTypeItem(
            modifier = modifier,
            icon = Icons.Default.BreakfastDining,
            text = "Breakfast",
            bgColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary
        )
        MealTypeItem(
            modifier = modifier,
            icon = Icons.Default.LunchDining,
            text = "Lunch",
            bgColor = MaterialTheme.colors.onPrimary,
            contentColor = MaterialTheme.colors.primary
        )
        Spacer(modifier = modifier.height(normalPadding))
        MealTypeItem(
            modifier = modifier,
            icon = Icons.Default.BrunchDining,
            text = "Snack",
            bgColor = MaterialTheme.colors.onPrimary,
            contentColor = MaterialTheme.colors.primary
        )
        MealTypeItem(
            modifier = modifier,
            icon = Icons.Default.DinnerDining,
            text = "Dinner",
            bgColor = MaterialTheme.colors.onPrimary,
            contentColor = MaterialTheme.colors.primary
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MealTypeItem(
    modifier: Modifier,
    icon: ImageVector,
    bgColor: Color = MaterialTheme.colors.onPrimary,
    contentColor: Color = MaterialTheme.colors.primary,
    text: String,
    onClick: () -> Unit = {}
) {
    Card(modifier = modifier
        .width(120.dp)
        .height(36.dp),
        shape = Shapes.large,
        border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
        backgroundColor = bgColor,
        onClick = {
            onClick()
        }) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = smallPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = modifier.size(16.dp),
                imageVector = icon,
                contentDescription = null,
                tint = contentColor
            )
            Spacer(modifier = modifier.width(6.dp))
            Text(
                text = text, style = MaterialTheme.typography.button.copy(
                    contentColor
                )
            )
        }
    }
}