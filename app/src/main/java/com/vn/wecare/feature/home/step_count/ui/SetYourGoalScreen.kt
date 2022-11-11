package com.vn.wecare.feature.home.step_count

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.ui.theme.*
import com.vn.wecare.utils.common_composable.CardListTile
import com.vn.wecare.utils.common_composable.Picker
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun SetYourGoalScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit
) {

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    val showModalBottomSheet = rememberSaveable {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()


    ModalBottomSheetLayout(
        sheetContent = { ModalBottomSheetContent(modifier = modifier) },
        sheetState = bottomSheetState
    ) {
        Scaffold(
            modifier = modifier,
            backgroundColor = MaterialTheme.colors.background,
            topBar = { SetYourGoalAppBar(modifier = modifier, navigateUp = navigateUp) }
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(smallPadding)
            ) {
                Spacer(modifier = modifier.height(smallPadding))
                CardListTile(
                    modifier = modifier,
                    leadingIconRes = R.drawable.ic_step,
                    trailingIconRes = R.drawable.ic_arrow_forward,
                    titleRes = R.string.footstep_title,
                    subTitle = "6000",
                    elevation = smallElevation,
                    onClick = {
                        showModalBottomSheet.value = !showModalBottomSheet.value
                        scope.launch { bottomSheetState.show() }
                    }
                )
                Spacer(modifier = modifier.height(halfMidPadding))
                CardListTile(
                    modifier = modifier,
                    leadingIconRes = R.drawable.ic_fire_calo,
                    trailingIconRes = R.drawable.ic_arrow_forward,
                    titleRes = R.string.calo_amount_title,
                    subTitle = "500 kcal",
                    colorIconRes = R.color.Red400,
                    elevation = smallElevation
                )
                Spacer(modifier = modifier.height(halfMidPadding))
                CardListTile(
                    modifier = modifier,
                    leadingIconRes = R.drawable.ic_time_clock,
                    trailingIconRes = R.drawable.ic_arrow_forward,
                    titleRes = R.string.move_min_title,
                    subTitle = "30 min",
                    colorIconRes = R.color.Blue400,
                    elevation = smallElevation
                )
            }
        }
    }
}

@Composable
fun SetYourGoalAppBar(
    modifier: Modifier,
    navigateUp: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = stringResource(id = R.string.set_goals))
        },
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null
                )
            }
        },
        backgroundColor = MaterialTheme.colors.background,
    )
}

@Composable
fun ModalBottomSheetContent(
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .heightIn()
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = midPadding, vertical = mediumPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Steps", style = MaterialTheme.typography.h3)
        Picker(
            list = listOf(
                1000,
                2000,
                3000,
                4000,
                5000,
                6000,
                7000,
                8000,
                9000,
                10000,
                11000,
                12000,
                13000
            ),
            modifier = modifier
                .padding(vertical = normalPadding, horizontal = normalPadding)
                .height(100.dp)
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.background)
        )
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                modifier = modifier
                    .weight(1f)
                    .padding(end = smallPadding)
                    .height(40.dp),
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(mediumRadius)
            ) {
                Text(text = stringResource(id = R.string.close_dialog_title))
            }
            Button(
                modifier = modifier
                    .weight(1f)
                    .padding(start = smallPadding)
                    .height(40.dp),
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(mediumRadius)
            ) {
                Text(text = stringResource(id = R.string.okay_dialog_title))
            }
        }
    }
}