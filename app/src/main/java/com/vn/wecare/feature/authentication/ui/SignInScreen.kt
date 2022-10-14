package com.vn.wecare.feature.authentication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.DefaultTintColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.vn.wecare.R
import com.vn.wecare.ui.theme.Green500
import com.vn.wecare.ui.theme.WecareTheme

@Composable
fun CustomTextField(
    hint: String?,
    label: String?,
    backgroundColor: Color? = null,
    cursorColor: Color? = null,
    focusedIndicatorColor: Color? = null,
    padding: Int = 0,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    TextField(
        modifier = Modifier
            .padding(padding.dp)
            .fillMaxWidth(),
        value = text,
        onValueChange = {
            text = it
        },
        placeholder = { if (hint != null) Text(text = hint) },
        label = { if (label != null) Text(text = label) },
        singleLine = true,
        leadingIcon = {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = "leadingIcon",
                    tint = Green500,
                )

            }
        },
        trailingIcon = {
            if (trailingIcon != null) {
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = "trailingIcon",
                    tint = Green500,
                )

            }
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = backgroundColor ?: DefaultTintColor,
            cursorColor = cursorColor ?: DefaultTintColor,
            focusedIndicatorColor = focusedIndicatorColor ?: DefaultTintColor
        )
    )
}

@Composable
fun CoilSVG(imageUrl: String, size: Int, description: String, padding: Int = 0) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .decoderFactory(SvgDecoder.Factory())
            .data(imageUrl.trim())
            .size(size)
            .build()
    )
    Image(
        painter = painter,
        description,
        modifier = Modifier.padding(padding.dp)
    )
}

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    padding: Int = 0,
    backgroundColor: Color = Green500,
    textColor: Color = Color.Black
) {
    Button(
        modifier = Modifier
            .padding(padding.dp)
            .fillMaxWidth(),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor)
    ) {
        Text(text = text, color = textColor)

    }
}

@Composable
fun SignInScreen(
    navigateToHome: () -> Unit
) {
    WecareTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //CoilSVG(imageUrl = "res/drawable/logo2.png", size = 50, description = "Logo svg", padding = 20)

            Image(
                modifier = Modifier.width(176.dp).height(100.dp).padding(top = 32.dp),
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
                cursorColor = Green500,
                focusedIndicatorColor = Color.Gray,
                leadingIcon = Icons.Default.Email,
                padding = 16
            )

            CustomTextField(
                hint = "Enter your password",
                label = "Password",
                backgroundColor = Color.White,
                cursorColor = Green500,
                focusedIndicatorColor = Color.Gray,
                leadingIcon = Icons.Filled.VpnKey,
                trailingIcon = Icons.Filled.Visibility,
                padding = 16
            )

            Text(
                text = "Forgot password?",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Right,
                color = Green500,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            CustomButton(
                text = "SIGN IN",
                onClick = navigateToHome,
                textColor = Color.White,
                padding = 16
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.width(100.dp) )

                Text(
                    text = " or continue with ",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(16.dp)
                )

                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.width(100.dp))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.width(80.dp).height(80.dp).padding(16.dp),
                    painter = painterResource(R.drawable.facebook),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds
                )

                Image(
                    modifier = Modifier.width(80.dp).height(80.dp).padding(16.dp),
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

                Text(
                    text = " Sign Up",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    color = Green500
                )
            }
        }
    }
}