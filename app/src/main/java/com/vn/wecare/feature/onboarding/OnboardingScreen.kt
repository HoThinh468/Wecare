package com.vn.wecare.feature.onboarding

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.feature.onboarding.composable.BaseOnboardingContent
import com.vn.wecare.feature.onboarding.composable.OnboardingAgePicker
import com.vn.wecare.feature.onboarding.composable.OnboardingGenderSelection
import com.vn.wecare.ui.theme.mediumRadius
import com.vn.wecare.ui.theme.midPadding

private const val ONBOARDING_PAGE_COUNT = 2

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(bottomBar = { BottomNav(modifier = modifier, index = 1) }) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .width(176.dp)
                    .height(100.dp)
                    .padding(top = 32.dp),
                painter = painterResource(R.drawable.logo2),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            HorizontalPager(pageCount = ONBOARDING_PAGE_COUNT) {
                when (it) {
                    0 -> {
                        BaseOnboardingContent(
                            modifier = modifier,
                            titleRes = R.string.gender_selection_title,
                            subtitleRes = R.string.gender_selection_subtitle
                        ) {
                            OnboardingGenderSelection(modifier = modifier)
                        }
                    }
                    1 -> {
                        BaseOnboardingContent(
                            modifier = modifier,
                            titleRes = R.string.age_picker_title,
                            subtitleRes = R.string.age_picker_subtitle
                        ) {
                            OnboardingAgePicker(modifier = modifier)
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}

@Composable
fun BottomNav(
    modifier: Modifier,
    index: Int,
) {
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
                modifier = modifier.clickable { /* TODO */ },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_double_arrow),
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary
                )
                if (index > 0) {
                    Text(
                        text = "Previous",
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(mediumRadius),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onPrimary)
            ) {
                Text(
                    text = if (index == 3) "Finish" else "Continue",
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.onSurface
                )
            }
        }
    }
}