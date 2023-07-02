package com.vn.wecare.feature.onboarding.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.feature.onboarding.ONBOARDING_PAGE_COUNT
import com.vn.wecare.ui.theme.mediumRadius
import com.vn.wecare.ui.theme.midPadding

@Composable
fun OnboardingBottomNav(
    modifier: Modifier, index: Int, onContinueClick: () -> Unit, onPreviousClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier
                .height(40.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            repeat(ONBOARDING_PAGE_COUNT) { iteration ->
                val color =
                    if (index == iteration) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
                val width = if (index == iteration) 20.dp else 10.dp
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .height(10.dp)
                        .width(width)
                )
            }
        }
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(90.dp),
            backgroundColor = MaterialTheme.colors.primary,
            elevation = 0.dp,
            shape = RoundedCornerShape(
                topStart = mediumRadius,
                topEnd = mediumRadius,
            )
        ) {
            Row(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = midPadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = modifier.clickable { onPreviousClick() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (index > 0) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_double_arrow),
                            contentDescription = null,
                            tint = MaterialTheme.colors.onPrimary
                        )
                        Text(
                            text = "Previous",
                            style = MaterialTheme.typography.button,
                            color = MaterialTheme.colors.onPrimary
                        )
                    }
                }
                Button(
                    onClick = onContinueClick,
                    shape = RoundedCornerShape(mediumRadius),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onPrimary)
                ) {
                    Text(
                        text = if (index == 4) "Finish" else "Continue",
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
        }
    }
}