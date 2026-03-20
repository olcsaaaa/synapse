package com.olidev.synapse_mobile.ui.deck_details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.unit.dp
import com.olidev.synapse_mobile.R
import com.olidev.synapse_mobile.ui.theme.SynapseSpacing

@OptIn(ExperimentalTextApi::class)
@Composable
fun AddCardListItem(
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val robotoFlexAction = FontFamily(
        Font(
            resId = R.font.roboto_flex,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(700),
                FontVariation.width(130f)
            )
        )
    )

    Surface(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = MaterialTheme.shapes.extraLarge,
        color = Color.Transparent,
        border = BorderStroke(
            width = 2.dp,
            color = accentColor.copy(alpha = 0.7f)
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.round_add_24),
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(SynapseSpacing.Medium))
            Text(
                text = "Add Card",
                style = MaterialTheme.typography.titleMedium,
                fontFamily = robotoFlexAction,
                color = accentColor
            )
        }
    }
}