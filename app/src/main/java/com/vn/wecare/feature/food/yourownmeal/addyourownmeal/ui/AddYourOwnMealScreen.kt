package com.vn.wecare.feature.food.yourownmeal.addyourownmeal.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.yourownmeal.addyourownmeal.viewmodel.AddYourOwnMealViewModel
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.largePadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.xxExtraPadding
import com.vn.wecare.utils.common_composable.LoadingDialog
import com.vn.wecare.utils.common_composable.WecareAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddYourOwnMealScreen(
    modifier: Modifier = Modifier, navigateBack: () -> Unit, viewModel: AddYourOwnMealViewModel
) {
    val focusManager = LocalFocusManager.current

    val uiState = viewModel.addMealUiState.collectAsState().value

    uiState.saveMealToFirebaseResponse.let {
        when (it) {
            is Response.Loading -> {
                LoadingDialog(loading = it == Response.Loading) {}
            }

            is Response.Success -> {
                Toast.makeText(LocalContext.current, "Save meal successfully", Toast.LENGTH_SHORT)
                    .show()
            }

            is Response.Error -> {
                Toast.makeText(
                    LocalContext.current, "Save meal fail, please try later!", Toast.LENGTH_SHORT
                ).show()
            }

            else -> { /* Do nothing */
            }
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus()
                    },
                )
            },
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            WecareAppBar(
                modifier = modifier, title = "Add your own meal",
                onLeadingIconPress = navigateBack,
            )
        },
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(midPadding)
                .verticalScroll(rememberScrollState())
        ) {
            NameAndCaloriesSection(
                modifier = modifier,
                mealName = viewModel.mealName,
                onClearMealNameClick = viewModel::clearMealName,
                onNameChange = viewModel::onNameChange,
                calories = viewModel.calories,
                onClearCaloriesClick = viewModel::clearCalories,
                onCaloriesChange = viewModel::onCaloriesChange,
                isNameValid = uiState.isNameValid,
                isCaloriesValid = uiState.isCaloriesValid,
                protein = uiState.protein,
                fat = uiState.fat,
                carbs = uiState.carbs
            )
            Spacer(modifier = modifier.height(normalPadding))
            CategoryAndImageSection(
                modifier = modifier,
                chosenMealTypeKey = uiState.currentChosenCategory,
                onItemClick = {
                    viewModel.updateChosenCategory(it)
                },
                imageUri = uiState.imageUri,
                onImagePick = {
                    viewModel.updateImageUri(it)
                },
                isImageUploaded = uiState.isImageUploaded
            )
            Spacer(modifier = modifier.height(largePadding))
            Button(
                modifier = modifier
                    .padding(horizontal = xxExtraPadding)
                    .fillMaxWidth()
                    .height(40.dp),
                onClick = {
                    viewModel.onSaveMealClick()
                },
                shape = Shapes.large,
            ) {
                Text(text = "SAVE", style = MaterialTheme.typography.button)
            }
            Spacer(modifier = modifier.height(xxExtraPadding))
        }
    }
}