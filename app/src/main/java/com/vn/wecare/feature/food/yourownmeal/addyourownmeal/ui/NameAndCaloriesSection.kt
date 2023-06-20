package com.vn.wecare.feature.food.yourownmeal.addyourownmeal.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Title
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.vn.wecare.feature.food.yourownmeal.addyourownmeal.viewmodel.AddYourOwnMealViewModel
import com.vn.wecare.feature.food.mealdetail.NutrientIndexItem
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Yellow
import com.vn.wecare.ui.theme.normalPadding

@Composable
fun NameAndCaloriesSection(
    modifier: Modifier,
    mealName: String,
    onClearMealNameClick: () -> Unit,
    onNameChange: (new: String) -> Unit,
    calories: String,
    onClearCaloriesClick: () -> Unit,
    onCaloriesChange: (new: String) -> Unit,
    isNameValid: Boolean,
    isCaloriesValid: Boolean,
    protein: Int,
    fat: Int,
    carbs: Int,
) {
    Text("Name", style = MaterialTheme.typography.body1)
    OutlinedTextField(modifier = modifier.fillMaxWidth(), value = mealName, onValueChange = {
        onNameChange(it)
    }, label = { Text("Meal's name") }, maxLines = 1, singleLine = true, leadingIcon = {
        Icon(imageVector = Icons.Default.Title, contentDescription = null)
    }, trailingIcon = {
        IconButton(onClick = { onClearMealNameClick() }) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
        }
    }, isError = !isNameValid
    )
    Spacer(modifier = modifier.height(normalPadding))
    Text("Calories", style = MaterialTheme.typography.body1)
    OutlinedTextField(modifier = modifier.fillMaxWidth(),
        value = calories,
        onValueChange = {
            onCaloriesChange(it)
        },
        label = { Text("Meal's calories") },
        maxLines = 1,
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Default.LocalFireDepartment, contentDescription = null)
        },
        trailingIcon = {
            IconButton(onClick = { onClearCaloriesClick() }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
        isError = !isCaloriesValid
    )
    Spacer(modifier = modifier.height(normalPadding))
    Row(
        modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NutrientIndexItem(
            modifier = modifier, title = "PROTEIN", index = "${protein}g", color = Red400
        )
        NutrientIndexItem(
            modifier = modifier, title = "FAT", index = "${fat}g", color = Yellow
        )
        NutrientIndexItem(
            modifier = modifier, title = "CARBS", index = "${carbs}g", color = Blue
        )
    }
}