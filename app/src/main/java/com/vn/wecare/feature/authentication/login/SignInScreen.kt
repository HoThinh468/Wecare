package com.vn.wecare.feature.authentication.login

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.utils.common_composable.CustomButton
import com.vn.wecare.utils.common_composable.CustomTextField
import com.vn.wecare.utils.common_composable.LoadingDialog

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SignInScreen(
    navigateToHome: () -> Unit,
    navigateToSignUp: () -> Unit,
    viewModel: LoginViewModel,
    moveToForgotPasswordScreen: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {

    val logInUiState = viewModel.logInUiState.collectAsState()
    val focusManager = LocalFocusManager.current

    val token = stringResource(R.string.default_web_client_id)
    val context = LocalContext.current
    val launcher = rememberFirebaseGoogleAuthLauncher(viewModel = viewModel)

    logInUiState.value.authenticationResponse.let {
        when (it) {
            is Response.Loading -> {
                LoadingDialog(loading = it == Response.Loading) {}
            }
            is Response.Success -> {
                viewModel.handleLoginSuccess(navigateToHome)
            }
            is Response.Error -> {
                viewModel.handleLoginError()
            }
            else -> { /* do nothing */
            }
        }
    }

    logInUiState.value.snackbarMessageRes?.let {
        val snackBarMessage = stringResource(id = it)
        LaunchedEffect(scaffoldState, viewModel, it, snackBarMessage) {
            scaffoldState.snackbarHostState.showSnackbar(snackBarMessage)
            viewModel.snackbarMessageShown()
        }
    }

    Scaffold(scaffoldState = scaffoldState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }, horizontalAlignment = Alignment.CenterHorizontally
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
                style = MaterialTheme.typography.h2,
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
                leadingIcon = Icons.Default.Email,
                value = viewModel.inputEmail,
                onValueChange = viewModel::onEmailChange,
                isError = !logInUiState.value.isEmailValid,
                errorMessage = stringResource(
                    id = viewModel.getEmailErrorMessage() ?: R.string.blank_text_id
                )
            )

            Box(modifier = Modifier.height(smallPadding))

            CustomTextField(
                hint = "Enter your password",
                label = "Password",
                backgroundColor = Color.White,
                cursorColor = MaterialTheme.colors.primary,
                leadingIcon = Icons.Filled.VpnKey,
                trailingIcon = if (viewModel.isPasswordShow) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                value = viewModel.inputPassword,
                onValueChange = viewModel::onPasswordChange,
                isTextVisible = viewModel.isPasswordShow,
                onShowTextClick = viewModel::onShowPasswordClick,
                isError = !logInUiState.value.isPasswordValid,
                errorMessage = stringResource(
                    id = viewModel.getPasswordErrorMessage() ?: R.string.blank_text_id
                )
            )

            Text(text = "Forgot password?",
                textAlign = TextAlign.Right,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .clickable {
                        moveToForgotPasswordScreen()
                        viewModel.clearLogInInformation()
                    }
                    .padding(halfMidPadding)
                    .align(Alignment.End),
                style = MaterialTheme.typography.button)

            CustomButton(
                text = "SIGN IN",
                onClick = {
                    viewModel.onSignInClick()
                },
                textColor = Color.White,
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
                    contentScale = ContentScale.FillBounds,
                )
                Image(
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                        .padding(16.dp)
                        .clickable {
                            val gso = GoogleSignInOptions
                                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(token)
                                .requestEmail()
                                .build()
                            val googleSignInClient = GoogleSignIn.getClient(context, gso)
                            launcher.launch(googleSignInClient.signInIntent)
                        },
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

                TextButton(onClick = {
                    navigateToSignUp()
                    viewModel.clearLogInInformation()
                }) {
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
}

@Composable
fun rememberFirebaseGoogleAuthLauncher(
    viewModel: LoginViewModel,
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            val account = task.getResult(ApiException::class.java)
            try {
                viewModel.onSignInWithGoogleClick(account.idToken!!)
            } catch (e: ApiException) {
                Log.d("Sign in with Google error: ", e.message.toString())
                viewModel.handleLoginError()
            }
        } else viewModel.handleLoginError()
    }
}