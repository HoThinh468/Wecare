package com.vn.wecare.utils.common_composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import com.vn.wecare.utils.WecareUserConstantValues.EXPANDABLE_TIME_MILLIS

@Composable
fun VerticalExpandableView(
    header: @Composable () -> Unit,
    expandablePart: @Composable () -> Unit,
    isExpanded: Boolean = false
) {
    val enterTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top, animationSpec = tween(EXPANDABLE_TIME_MILLIS)
        ) + fadeIn(
            animationSpec = tween(EXPANDABLE_TIME_MILLIS)
        )
    }

    val collapseTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top, animationSpec = tween(EXPANDABLE_TIME_MILLIS)
        ) + fadeOut(
            animationSpec = tween(EXPANDABLE_TIME_MILLIS)
        )
    }

    Column(horizontalAlignment = Alignment.Start) {
        header()
        AnimatedVisibility(
            visible = isExpanded, enter = enterTransition, exit = collapseTransition
        ) {
            expandablePart()
        }
    }
}