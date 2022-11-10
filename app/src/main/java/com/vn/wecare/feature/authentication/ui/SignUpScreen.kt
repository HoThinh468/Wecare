package com.vn.wecare.feature.authentication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vn.wecare.R
import com.vn.wecare.ui.theme.WecareTheme

@Composable
fun SignUpScreen() {
    WecareTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //CoilSVG(imageUrl = "res/drawable/logo2.png", size = 50, description = "Logo svg", padding = 20)

            Image(
                modifier = Modifier.width(200.dp).height(58.dp).padding(start = 24.dp),
                painter = painterResource(R.drawable.logo2),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )

            Text(
                text = "Create account",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            )

            CustomTextField(
                hint = "Enter your email",
                label = "Email",
                backgroundColor = Color.White,
                cursorColor = MaterialTheme.colors.primary,
                focusedIndicatorColor = Color.Gray,
                leadingIcon = Icons.Default.Email,
                padding = 16
            )

            CustomTextField(
                hint = "Enter your username",
                label = "User Name",
                backgroundColor = Color.White,
                cursorColor = MaterialTheme.colors.primary,
                focusedIndicatorColor = Color.Gray,
                leadingIcon = Icons.Default.Person,
                padding = 16
            )

            CustomTextField(
                hint = "Enter your password",
                label = "Password",
                backgroundColor = Color.White,
                cursorColor = MaterialTheme.colors.primary,
                focusedIndicatorColor = Color.Gray,
                leadingIcon = Icons.Filled.VpnKey,
                trailingIcon = Icons.Filled.Visibility,
                padding = 16
            )

            CustomButton(
                text = "SIGN UP",
                onClick = { /*TODO*/ },
                textColor = Color.White,
                padding = 32
            )

            Row(
                modifier = Modifier.padding(start = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an account?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )

                Text(
                    text = " Sign In",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}