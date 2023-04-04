package com.vn.wecare.feature.onboarding.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.ui.theme.xxxExtraPadding

@Composable
fun BaseOnboardingContent(
    modifier: Modifier,
    @StringRes titleRes: Int,
    @StringRes subtitleRes: Int,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .padding(mediumPadding)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = modifier.padding(vertical = smallPadding),
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = modifier.padding(horizontal = mediumPadding),
            text = stringResource(id = subtitleRes),
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = modifier.height(xxxExtraPadding))
        content()
    }
}