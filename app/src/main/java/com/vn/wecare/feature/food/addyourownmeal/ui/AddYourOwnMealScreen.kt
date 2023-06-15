package com.vn.wecare.feature.food.addyourownmeal.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import com.vn.wecare.feature.food.addyourownmeal.AddYourOwnMealViewModel
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.utils.common_composable.WecareAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddYourOwnMealScreen(
    modifier: Modifier = Modifier, navigateBack: () -> Unit, viewModel: AddYourOwnMealViewModel
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            WecareAppBar(
                modifier = modifier, title = "My own meal", onLeadingIconPress = navigateBack
            )
        },
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(midPadding)
                .verticalScroll(rememberScrollState())
        ) {
            NameAndDescriptionSection(modifier = modifier)
            Spacer(modifier = modifier.height(normalPadding))
            CategoryAndImageSection(modifier = modifier)
        }
    }
}