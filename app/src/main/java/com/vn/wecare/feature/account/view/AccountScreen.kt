package com.vn.wecare.feature.account.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.ui.theme.*
import com.vn.wecare.utils.common_composable.CardListTile

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    onSignOutClick: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        topBar = {
            AccountHeader(modifier = modifier) { navigateUp() }
        },
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(halfMidPadding),
        ) {
            AccountBody(
                modifier = modifier,
                onSignOutClick =  onSignOutClick
            )
        }
    }
}

@Composable
fun AccountHeader(
    modifier: Modifier, navigateUp: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = tinyPadding)
                .background(color = MaterialTheme.colors.background),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = navigateUp) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null
                )
            }
            Text(
                modifier = modifier.padding(horizontal = smallPadding),
                text = stringResource(id = R.string.account_title),
                style = MaterialTheme.typography.h4,
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.padding(vertical = halfMidPadding)
        ) {
            Card(
                modifier = modifier.size(80.dp),
                shape = CircleShape,
                backgroundColor = MaterialTheme.colors.primary,
            ) {}
            Text(
                text = "T",
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onPrimary
            )
        }
        Text(text = "Thinh Ho", style = MaterialTheme.typography.h3)
        Text(
            text = "htt@gmail.com", style = MaterialTheme.typography.body1, color = colorResource(
                id = R.color.Black450
            ), modifier = modifier.padding(bottom = normalPadding)
        )
    }
}

@Composable
fun AccountBody(
    modifier: Modifier,
    onSignOutClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CardListTile(
            modifier = modifier,
            leadingIconRes = R.drawable.ic_baseline_key,
            trailingIconRes = R.drawable.ic_arrow_forward,
            titleRes = R.string.change_password,
            subTitle = "Forgot your password? Change it now",
            elevation = 0.dp,
            colorIconRes = R.color.Orange300
        )
        Spacer(modifier = modifier.height(halfMidPadding))
        CardListTile(
            modifier = modifier,
            leadingIconRes = R.drawable.ic_edit,
            trailingIconRes = R.drawable.ic_arrow_forward,
            titleRes = R.string.edit_information,
            subTitle = "Change your height and weight",
            elevation = 0.dp,
            colorIconRes = R.color.Pink
        )
        Spacer(modifier = modifier.height(halfMidPadding))
        CardListTile(
            modifier = modifier,
            leadingIconRes = R.drawable.ic_question_mark,
            trailingIconRes = R.drawable.ic_arrow_forward,
            titleRes = R.string.about_us,
            subTitle = "Check more about us",
            elevation = 0.dp,
            colorIconRes = R.color.Blue400
        )
        Spacer(modifier = modifier.height(halfMidPadding))
        CardListTile(
            modifier = modifier,
            leadingIconRes = R.drawable.ic_logout,
            trailingIconRes = null,
            titleRes = R.string.logout,
            subTitle = null,
            elevation = 0.dp,
            colorIconRes = R.color.Red400,
            onClick = onSignOutClick
        )
    }
}