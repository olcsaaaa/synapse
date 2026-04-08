package com.olidev.synapse_mobile.ui.deck_details

import android.view.Surface
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.olidev.synapse_mobile.R
import com.olidev.synapse_mobile.ui.theme.SynapseSpacing

@OptIn(ExperimentalTextApi::class)
@Composable
fun FlashcardInputArea(
    state: TextFieldState,
    label: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    val spacing = SynapseSpacing

    val robotoFlexSansLabel = FontFamily(
        Font(
            resId = R.font.roboto_flex,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(800),
                FontVariation.width(150f)
            )
        )
    )

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = label, style = MaterialTheme.typography.labelLarge.copy(
                fontFamily = robotoFlexSansLabel,
                letterSpacing = 1.sp,
            ),
            color = accentColor,
            modifier = Modifier
                .padding(horizontal = spacing.Small)
                .padding(bottom = spacing.Small)
        )
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.surfaceContainerLowest,
            border = BorderStroke(
                width = 2.dp,
                color = accentColor.copy(alpha = 0.2f)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                state = state,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.Small),
                lineLimits = TextFieldLineLimits.Default,
                colors = colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = accentColor,
                    selectionColors = TextSelectionColors(
                        handleColor = accentColor,
                        backgroundColor = accentColor.copy(alpha = 0.2f)
                    )
                ),
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}