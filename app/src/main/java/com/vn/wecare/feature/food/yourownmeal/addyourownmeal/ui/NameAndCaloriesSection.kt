package com.vn.wecare.feature.food.yourownmeal.addyourownmeal.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Title
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.smallPadding

@Composable
fun NameAndNutrientsSection(
    modifier: Modifier,
    mealName: String,
    onClearMealNameClick: () -> Unit,
    onNameChange: (new: String) -> Unit,
    isNameValid: Boolean,
    protein: String,
    onProteinChange: (new: String) -> Unit,
    fat: String,
    onFatChange: (new: String) -> Unit,
    carbs: String,
    onCarbsChange: (new: String) -> Unit,
    calories: String,
    areNutrientsValid: Boolean
) {
    Text("Name", style = MaterialTheme.typography.body1)
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(), value = mealName,
        onValueChange = {
            onNameChange(it)
        },
        label = { Text("Meal's name") }, maxLines = 1, singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Title, contentDescription = null)
        },
        trailingIcon = {
            IconButton(onClick = { onClearMealNameClick() }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        },
        isError = !isNameValid,
    )
    Spacer(modifier = modifier.height(smallPadding))
    Text("Nutrients (gram)", style = MaterialTheme.typography.body1)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = smallPadding),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedTextField(
            modifier = modifier.weight(1f),
            value = protein,
            onValueChange = {
                onProteinChange(it)
            },
            label = { Text("Protein") },
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
        )
        Spacer(modifier = modifier.width(smallPadding))
        OutlinedTextField(
            modifier = modifier.weight(1f),
            value = fat,
            onValueChange = {
                onFatChange(it)
            },
            label = { Text("Fat") },
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
        )
        Spacer(modifier = modifier.width(smallPadding))
        OutlinedTextField(
            modifier = modifier.weight(1f),
            value = carbs,
            onValueChange = {
                onCarbsChange(it)
            },
            label = { Text("Carbs") },
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
        )
    }
    if (!areNutrientsValid) {
        Text(
            text = "*All the nutrients value must be greater than 0",
            style = MaterialTheme.typography.caption.copy(color = Red400)
        )
    }
    Text(
        text = "Estimated calories => $calories cal",
        style = MaterialTheme.typography.button.copy(color = MaterialTheme.colors.primary)
    )
}