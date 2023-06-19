package com.vn.wecare.feature.food.yourownmeal.yourownmeal

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BreakfastDining
import androidx.compose.material.icons.filled.BrunchDining
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vn.wecare.R
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.feature.food.search.ui.getBgColorFromMealTypeKey
import com.vn.wecare.feature.food.search.ui.getContentColorFromMealTypeKey
import com.vn.wecare.feature.food.yourownmeal.addyourownmeal.ui.MealTypeItem
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.utils.common_composable.WecareAppBar

@Composable
fun YourOwnMealTopBar(
    modifier: Modifier,
    moveToAddYourOwnMealScreen: () -> Unit,
    navigateBack: () -> Unit,
    chosenMealTypeKey: MealTypeKey,
    onItemClick: (key: MealTypeKey) -> Unit,
    mealName: String,
    onClearSearchBox: () -> Unit,
    onSearchTextChanged: (newText: String) -> Unit
) {
    Column {
        WecareAppBar(
            modifier = modifier,
            title = "My own meals",
            onLeadingIconPress = navigateBack,
            trailingIconRes = R.drawable.ic_add,
            onTrailingIconPress = {
                moveToAddYourOwnMealScreen()
            },
        )
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = midPadding),
            value = mealName,
            onValueChange = {
                onSearchTextChanged(it)
            },
            label = { Text("Enter meal name") },
            maxLines = 1,
            singleLine = true,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            trailingIcon = {
                IconButton(onClick = { onClearSearchBox() }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }
            },
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = midPadding, vertical = smallPadding)
                .horizontalScroll(rememberScrollState())
        ) {
            MealTypeItem(
                modifier = modifier,
                icon = Icons.Default.BreakfastDining,
                text = "Breakfast",
                bgColor = getBgColorFromMealTypeKey(
                    chosen = chosenMealTypeKey, key = MealTypeKey.BREAKFAST
                ),
                contentColor = getContentColorFromMealTypeKey(
                    chosen = chosenMealTypeKey, key = MealTypeKey.BREAKFAST
                ),
                onClick = {
                    onItemClick(MealTypeKey.BREAKFAST)
                },
            )
            Spacer(modifier = modifier.width(normalPadding))
            MealTypeItem(
                modifier = modifier,
                icon = Icons.Default.LunchDining,
                text = "Lunch",
                bgColor = getBgColorFromMealTypeKey(
                    chosen = chosenMealTypeKey, key = MealTypeKey.LUNCH
                ),
                contentColor = getContentColorFromMealTypeKey(
                    chosen = chosenMealTypeKey, key = MealTypeKey.LUNCH
                ),
                onClick = {
                    onItemClick(MealTypeKey.LUNCH)
                },
            )
            Spacer(modifier = modifier.width(normalPadding))
            MealTypeItem(
                modifier = modifier,
                icon = Icons.Default.BrunchDining,
                text = "Snack",
                bgColor = getBgColorFromMealTypeKey(
                    chosen = chosenMealTypeKey, key = MealTypeKey.SNACK
                ),
                contentColor = getContentColorFromMealTypeKey(
                    chosen = chosenMealTypeKey, key = MealTypeKey.SNACK
                ),
                onClick = {
                    onItemClick(MealTypeKey.SNACK)
                },
            )
            Spacer(modifier = modifier.width(normalPadding))
            MealTypeItem(
                modifier = modifier,
                icon = Icons.Default.DinnerDining,
                text = "Dinner",
                bgColor = getBgColorFromMealTypeKey(
                    chosen = chosenMealTypeKey, key = MealTypeKey.DINNER
                ),
                contentColor = getContentColorFromMealTypeKey(
                    chosen = chosenMealTypeKey, key = MealTypeKey.DINNER
                ),
                onClick = {
                    onItemClick(MealTypeKey.DINNER)
                },
            )
        }
    }
}