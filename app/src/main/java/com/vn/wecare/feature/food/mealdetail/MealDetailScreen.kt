package com.vn.wecare.feature.food.mealdetail

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BreakfastDining
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vn.wecare.feature.food.data.model.MealRecordModel
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.Yellow
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.ui.theme.xxExtraPadding
import com.vn.wecare.utils.common_composable.WecareAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MealDetailScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    mealType: MealTypeKey,
    record: MealRecordModel,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            WecareAppBar(
                modifier = modifier, title = "", onLeadingIconPress = navigateUp
            )
        },
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = midPadding)
        ) {
            MealOverview(modifier = modifier, mealType = mealType, record = record)
            Spacer(modifier = modifier.height(xxExtraPadding))
            NutrientsIndexInformation(modifier = modifier, record = record)
            Spacer(modifier = modifier.height(xxExtraPadding))
        }
    }
}

@Composable
private fun MealOverview(
    modifier: Modifier, mealType: MealTypeKey, record: MealRecordModel
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = mediumPadding),
        text = record.title,
        style = MaterialTheme.typography.h3,
        textAlign = TextAlign.Center,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = midPadding, bottom = xxExtraPadding),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NutrientSubInfoItem(
            modifier = modifier,
            icon = Icons.Default.LocalFireDepartment,
            color = Red400,
            index = "${record.calories} cal"
        )
        Spacer(modifier = modifier.width(normalPadding))
        NutrientSubInfoItem(
            modifier = modifier,
            icon = Icons.Default.BreakfastDining,
            color = MaterialTheme.colors.primary,
            index = mealType.value
        )
    }
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        AsyncImage(
            model = record.imgUrl,
            contentDescription = null,
            modifier = modifier
                .width(this.maxWidth)
                .height(this.maxWidth * 0.9f)
                .clip(Shapes.large),
            contentScale = ContentScale.Crop,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NutrientSubInfoItem(
    modifier: Modifier,
    icon: ImageVector,
    color: Color = MaterialTheme.colors.primary,
    bgColor: Color = MaterialTheme.colors.background,
    contentColor: Color? = null,
    index: String,
    onClick: () -> Unit = {}
) {
    Card(shape = Shapes.large,
        border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
        backgroundColor = bgColor,
        onClick = {
            onClick()
        }) {
        Row(
            modifier = modifier.padding(horizontal = 12.dp, vertical = smallPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = modifier.size(16.dp),
                imageVector = icon,
                contentDescription = null,
                tint = contentColor ?: color
            )
            Spacer(modifier = modifier.width(4.dp))
            Text(
                text = index, style = MaterialTheme.typography.button.copy(
                    contentColor ?: MaterialTheme.colors.onSecondary.copy(0.5f)
                )
            )
        }
    }
}

@Composable
private fun NutrientsIndexInformation(modifier: Modifier, record: MealRecordModel) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = midPadding),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NutrientIndexItem(
            modifier = modifier,
            title = "PROTEIN",
            index = record.protein.dropLast(1).toInt(),
            color = Red400
        )
        NutrientIndexItem(
            modifier = modifier,
            title = "FAT",
            index = record.fat.dropLast(1).toInt(),
            color = Yellow
        )
        NutrientIndexItem(
            modifier = modifier,
            title = "CARBS",
            index = record.carbs.dropLast(1).toInt(),
            color = Blue
        )
    }
}

@Composable
fun NutrientIndexItem(modifier: Modifier, title: String, index: Int, color: Color) {
    Box(
        modifier = modifier
            .size(84.dp)
            .background(color = color, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = title,
                style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onPrimary)
            )
            Text(
                text = "${index}g",
                style = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.onPrimary)
            )
        }
    }
}