package com.vn.wecare.feature.authentication.ui.forgotpassword

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.vn.wecare.R
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.xxxExtraPadding
import com.vn.wecare.utils.common_composable.CustomButton

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SendRecoveryEmailSuccessScreen(
    modifier: Modifier = Modifier,
    moveToLoginScreen: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.background,
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(vertical = xxxExtraPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = modifier.padding(vertical = normalPadding, horizontal = mediumPadding),
                painter = painterResource(id = R.drawable.ill_successful),
                contentDescription = null
            )
            Text(
                text = stringResource(id = R.string.send_recovery_email_message),
                style = MaterialTheme.typography.body1,
                modifier = modifier.padding(horizontal = mediumPadding)
            )
            CustomButton(
                text = "Login now",
                onClick = { moveToLoginScreen() },
                textColor = Color.White,
                padding = mediumPadding
            )
        }
    }
}