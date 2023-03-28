package com.vn.wecare.feature.account.view

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.AccountUiState
import com.vn.wecare.feature.account.AccountViewModel
import com.vn.wecare.ui.theme.*
import com.vn.wecare.utils.common_composable.CardListTile
import com.vn.wecare.utils.common_composable.LoadingDialog

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    moveToSignInScreen: () -> Unit,
    viewModel: AccountViewModel,
) {

    val uiState = viewModel.accountUiState.collectAsState()

    uiState.value.signOutResponse.let {
        when (it) {
            is Response.Loading -> {
                LoadingDialog(loading = it == Response.Loading) {}
            }
            is Response.Success -> viewModel.handleSignOutSuccess {
                moveToSignInScreen()
            }
            is Response.Error -> viewModel.handleSignOutError()
            null -> {/* do nothing */
            }
        }
    }

    Scaffold(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        topBar = {
            AccountHeader(modifier = modifier,
                uiState = uiState,
                sendVerifiedEmail = viewModel::sendVerificationEmail,
                pickImageUri = {
                    viewModel.pickImageUriFromPhone(it)
                })
        },
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(halfMidPadding),
        ) {
            AccountBody(modifier = modifier, onSignOutClick = { viewModel.onSignOutClick() })
        }
    }
}

@Composable
fun AccountHeader(
    modifier: Modifier,
    uiState: State<AccountUiState>,
    sendVerifiedEmail: () -> Unit,
    pickImageUri: (uri: Uri?) -> Unit
) {

    val galleryLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent(),
            onResult = {
                pickImageUri(it)
            })

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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = modifier.padding(horizontal = smallPadding),
                text = stringResource(id = R.string.account_title),
                style = MaterialTheme.typography.h4,
            )
        }
        if (uiState.value.avatarUri == null) {
            Box(contentAlignment = Alignment.Center,
                modifier = modifier
                    .background(MaterialTheme.colors.primary, shape = CircleShape)
                    .size(80.dp)
                    .padding(vertical = halfMidPadding)
                    .clickable {
                        galleryLauncher.launch("image/*")
                    }) {
                Text(
                    text = uiState.value.userNameLogo,
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        } else {
            AsyncImage(
                model = uiState.value.avatarUri,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )
        }
        Text(
            text = uiState.value.username,
            style = MaterialTheme.typography.h3,
            modifier = modifier.padding(top = normalPadding)
        )
        Text(
            text = uiState.value.email,
            style = MaterialTheme.typography.body1,
            color = colorResource(
                id = R.color.Black450
            ),
            modifier = modifier.padding(bottom = normalPadding)
        )
        if (!uiState.value.isEmailVerified) {
            Button(
                onClick = sendVerifiedEmail, modifier = modifier.padding(bottom = normalPadding)
            ) {
                Text("Get your email verified!")
            }
        }
    }
}

@Composable
fun AccountBody(
    modifier: Modifier, onSignOutClick: () -> Unit
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