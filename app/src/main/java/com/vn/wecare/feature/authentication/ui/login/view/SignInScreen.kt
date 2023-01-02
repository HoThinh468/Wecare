package com.vn.wecare.feature.authentication.ui.login.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.vn.wecare.R
import com.vn.wecare.feature.authentication.ui.login.LoginViewModel
import com.vn.wecare.utils.common_composable.CustomButton
import com.vn.wecare.utils.common_composable.CustomTextField

@Composable
fun CoilSVG(imageUrl: String, size: Int, description: String, padding: Int = 0) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).decoderFactory(SvgDecoder.Factory())
            .data(imageUrl.trim()).size(size).build()
    )
    Image(
        painter = painter, description, modifier = Modifier.padding(padding.dp)
    )
}

@Composable
fun SignInScreen(
    navigateToHome: () -> Unit,
    navigateToSignUp: () -> Unit,
    viewModel: LoginViewModel,
    moveToForgotPasswordScreen: () -> Unit
) {

    val uiState by viewModel.loginUiState

    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .width(176.dp)
                .height(100.dp)
                .padding(top = 32.dp),
            painter = painterResource(R.drawable.logo2),
            contentDescription = "",
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = "Welcome Back",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        CustomTextField(
            hint = "Enter your email",
            label = "Email",
            backgroundColor = Color.White,
            cursorColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = Color.Gray,
            leadingIcon = Icons.Default.Email,
            value = uiState.email,
            onValueChange = viewModel::onEmailChange
        )

        CustomTextField(
            hint = "Enter your password",
            label = "Password",
            backgroundColor = Color.White,
            cursorColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = Color.Gray,
            leadingIcon = Icons.Filled.VpnKey,
            trailingIcon = if (uiState.isPasswordShow) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
            value = uiState.password,
            onValueChange = viewModel::onPasswordChange,
            isTextVisible = uiState.isPasswordShow,
            onShowTextClick = viewModel::onShowPasswordClick
        )

        TextButton(
            onClick = moveToForgotPasswordScreen
        ) {
            Text(
                text = "Forgot password?",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Right,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }

        CustomButton(
            text = "SIGN IN", onClick = {
                viewModel.onSignInClick(moveToHomeScreen = { navigateToHome() })
            }, textColor = Color.White
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.width(100.dp))

            Text(
                text = " or continue with ",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                modifier = Modifier.padding(16.dp)
            )

            Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.width(100.dp))
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
                    .padding(16.dp),
                painter = painterResource(R.drawable.facebook),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )

            Image(
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
                    .padding(16.dp),
                painter = painterResource(R.drawable.google),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don’t have an account?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )

            TextButton(onClick = navigateToSignUp) {
                Text(
                    text = " Sign Up",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primary
                )

            }
        }
    }
}