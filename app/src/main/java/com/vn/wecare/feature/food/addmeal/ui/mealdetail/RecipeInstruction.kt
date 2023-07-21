package com.vn.wecare.feature.food.addmeal.ui.mealdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.feature.food.data.model.Recipe.Instruction
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.normalPadding

@Composable
fun RecipeInstruction(
    modifier: Modifier, instructions: List<Instruction>
) {
    Text(text = "Recipe instruction", style = MaterialTheme.typography.h4)
    Spacer(modifier = modifier.height(normalPadding))
    if (instructions.isNotEmpty()) {
        for (step in instructions[0].steps) {
            CookingStepsItem(modifier = modifier, index = step.number, description = step.step)
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = modifier.size(200.dp),
                painter = painterResource(id = R.drawable.img_no_instruction_available),
                contentDescription = null
            )
            Spacer(modifier = modifier.height(normalPadding))
            Text(
                text = "Oops! No instruction for this meal, let's try another",
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun CookingStepsItem(modifier: Modifier, index: Int, description: String) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = normalPadding)
    ) {
        Text(text = "Step $index", style = MaterialTheme.typography.button)
        Text(
            modifier = modifier.padding(start = mediumPadding, top = 4.dp, bottom = halfMidPadding),
            text = description,
            style = MaterialTheme.typography.caption
        )
        Divider()
    }
}