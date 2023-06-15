package com.vn.wecare.feature.food.search.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vn.wecare.feature.food.search.SearchFoodViewModel
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.utils.common_composable.WecareAppBar

@Composable
fun SearchMealAppBar(
    modifier: Modifier, onBackPress: () -> Unit, viewModel: SearchFoodViewModel
) {

    val inputQuery = viewModel.inputQuery.collectAsState().value

    Column(
        modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WecareAppBar(
            modifier = modifier,
            title = "Meals",
            onLeadingIconPress = onBackPress,
        )
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = normalPadding),
            value = inputQuery,
            onValueChange = viewModel::onQueryChange,
            label = { Text("Search for meal") },
            maxLines = 1,
            singleLine = true,
            leadingIcon = {
                IconButton(onClick = { viewModel.clearQuery() }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    viewModel.onSearchForQuery()
                }) {
                    if (inputQuery.isNotEmpty()) {
                        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
                    }
                }
            },
            keyboardActions = KeyboardActions(onDone = {
                viewModel.onSearchForQuery()
            })
        )
    }
}