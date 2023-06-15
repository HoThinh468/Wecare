package com.vn.wecare.feature.food.search.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BreakfastDining
import androidx.compose.material.icons.filled.BrunchDining
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.feature.food.search.SearchFoodViewModel
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.ui.theme.smallRadius

@Composable
fun ChooseMealTypeKeyDialog(
    modifier: Modifier = Modifier,
    onAddButtonClick: () -> Unit,
    onCloseButtonClick: () -> Unit,
    viewModel: SearchFoodViewModel
) {

    val chosenMealTypeKey = viewModel.searchMealUiSate.collectAsState().value.currentChosenMealType

    Dialog(onDismissRequest = { onCloseButtonClick() }) {
        Surface(
            shape = RoundedCornerShape(smallRadius)
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(midPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("Choose meal type", style = MaterialTheme.typography.h5)
                Spacer(modifier = modifier.height(midPadding))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    MealTypeKeyItem(
                        modifier = modifier,
                        text = "Breakfast",
                        backgroundColor = getBgColorFromMealTypeKey(
                            chosen = chosenMealTypeKey, key = MealTypeKey.BREAKFAST
                        ),
                        contentColor = getContentColorFromMealTypeKey(
                            chosen = chosenMealTypeKey, key = MealTypeKey.BREAKFAST
                        ),
                        icon = Icons.Default.BreakfastDining
                    ) {
                        viewModel.updateChosenMealTypeKey(MealTypeKey.BREAKFAST)
                    }

                    MealTypeKeyItem(
                        modifier = modifier,
                        text = "Lunch",
                        backgroundColor = getBgColorFromMealTypeKey(
                            chosen = chosenMealTypeKey, key = MealTypeKey.LUNCH
                        ),
                        contentColor = getContentColorFromMealTypeKey(
                            chosen = chosenMealTypeKey, key = MealTypeKey.LUNCH
                        ),
                        icon = Icons.Default.LunchDining
                    ) {
                        viewModel.updateChosenMealTypeKey(MealTypeKey.LUNCH)
                    }
                }
                Spacer(modifier = modifier.height(normalPadding))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    MealTypeKeyItem(
                        modifier = modifier,
                        text = "Snack",
                        backgroundColor = getBgColorFromMealTypeKey(
                            chosen = chosenMealTypeKey, key = MealTypeKey.SNACK
                        ),
                        contentColor = getContentColorFromMealTypeKey(
                            chosen = chosenMealTypeKey, key = MealTypeKey.SNACK
                        ),
                        icon = Icons.Default.BrunchDining
                    ) {
                        viewModel.updateChosenMealTypeKey(MealTypeKey.SNACK)
                    }

                    MealTypeKeyItem(
                        modifier = modifier,
                        text = "Dinner",
                        backgroundColor = getBgColorFromMealTypeKey(
                            chosen = chosenMealTypeKey, key = MealTypeKey.DINNER
                        ),
                        contentColor = getContentColorFromMealTypeKey(
                            chosen = chosenMealTypeKey, key = MealTypeKey.DINNER
                        ),
                        icon = Icons.Default.DinnerDining
                    ) {
                        viewModel.updateChosenMealTypeKey(MealTypeKey.DINNER)
                    }
                }
                Spacer(modifier = modifier.height(mediumPadding))
                Button(
                    modifier = modifier
                        .padding(horizontal = midPadding)
                        .fillMaxWidth()
                        .height(40.dp),
                    onClick = {
                        onAddButtonClick()
                        onCloseButtonClick()
                    },
                    shape = Shapes.large,
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
                TextButton(onClick = { onCloseButtonClick() }) {
                    Text(text = "Close")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MealTypeKeyItem(
    modifier: Modifier,
    text: String,
    backgroundColor: Color,
    contentColor: Color,
    icon: ImageVector,
    onItemClick: () -> Unit,
) {
    Card(
        modifier = modifier.width(110.dp),
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        shape = RoundedCornerShape(smallRadius),
        onClick = { onItemClick() },
    ) {
        Column(
            modifier = modifier.padding(normalPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = icon, contentDescription = null, tint = contentColor
            )
            Spacer(modifier = modifier.height(smallPadding))
            Text(text = text, style = MaterialTheme.typography.body1)
        }
    }
}

@Composable
fun getBgColorFromMealTypeKey(chosen: MealTypeKey, key: MealTypeKey): Color {
    return if (chosen == key) {
        MaterialTheme.colors.primary
    } else MaterialTheme.colors.onPrimary
}

@Composable
fun getContentColorFromMealTypeKey(chosen: MealTypeKey, key: MealTypeKey): Color {
    return if (chosen == key) {
        MaterialTheme.colors.onPrimary
    } else MaterialTheme.colors.primary
}