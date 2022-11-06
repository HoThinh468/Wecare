package com.vn.wecare.feature.home.bmi

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.ui.theme.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun YourBMIHomeCard(
    modifier: Modifier,
    onCardClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = midPadding),
        elevation = smallElevation,
        onClick = onCardClick,
        shape = Shapes.small,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(normalPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Your BMI is normal",
                    style = MaterialTheme.typography.h4,
                )
                Text(
                    modifier = modifier.padding(top = smallPadding),
                    text = "20.5",
                    style = MaterialTheme.typography.h1.copy(
                        color = colorResource(id = R.color.Orange300)
                    )
                )
            }
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_happy),
                    contentDescription = null,
                    tint = colorResource(id = R.color.Green500)
                )
                Spacer(modifier = modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_upset),
                    contentDescription = null,
                    tint = colorResource(id = R.color.Grey500)
                )
                Spacer(modifier = modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_sad),
                    contentDescription = null,
                    tint = colorResource(id = R.color.Grey500)
                )
            }
        }
    }
}