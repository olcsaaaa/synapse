package com.olidev.synapse_mobile.ui.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.olidev.synapse_mobile.R
import com.olidev.synapse_mobile.data.helpers.ColorEnergy
import com.olidev.synapse_mobile.data.helpers.ColorPairing
import com.olidev.synapse_mobile.ui.theme.ColorFamily
import com.olidev.synapse_mobile.ui.theme.SynapseSpacing
import com.olidev.synapse_mobile.ui.theme.SynapseTheme
import kotlin.toString

val spacing = SynapseSpacing

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalTextApi::class
)
@Composable
fun AddDeckDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onSave: (name: String, description: String, colorSeed: Int) -> Unit = { _, _, _ -> }
) {
    val nameState = rememberTextFieldState(initialText = "")
    val descriptionState = rememberTextFieldState(initialText = "")
    var selectedColorSeed by remember { mutableIntStateOf(0) }

    val focusManager = LocalFocusManager.current

    val colors = colorScheme
    val extra = SynapseTheme.extraColors


    val colorPairings = remember(colors, extra) {
        listOf(
            ColorFamily(
                colors.primary,
                colors.onPrimary,
                colors.primaryContainer,
                colors.onPrimaryContainer
            ),
            ColorFamily(
                colors.secondary,
                colors.onSecondary,
                colors.secondaryContainer,
                colors.onSecondaryContainer
            ),
            ColorFamily(
                colors.tertiary,
                colors.onTertiary,
                colors.tertiaryContainer,
                colors.onTertiaryContainer
            ),
            extra.vividMagenta,
            extra.springGreen,
            extra.electricViolet
        )
    }
    Surface(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            },
        shape = RoundedCornerShape(SynapseSpacing.Hero),
        color = colorScheme.surfaceContainerLow,
        tonalElevation = 2.dp
    ) {
        BoxWithConstraints {
            val isHorizontal = maxWidth > 500.dp

            if (isHorizontal) {
                Column(
                    modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.padding(
                            top = spacing.ExtraLarge,
                            start = spacing.ExtraLarge,
                            end = spacing.ExtraLarge,
                            bottom = spacing.Small
                        ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextFieldColumn(
                            modifier = Modifier.weight(1.5f),
                            nameState = nameState,
                            descriptionState = descriptionState
                        )
                        ColorPicker(
                            modifier = Modifier.weight(1f),
                            colorPairings = colorPairings,
                            selectedColorSeed = selectedColorSeed,
                            onColorSelected = { selectedColorSeed = it }
                        )
                    }
                    CreateButton(
                        onClick = {
                            onSave(
                                nameState.text.toString(),
                                descriptionState.text.toString(),
                                selectedColorSeed
                            )
                        },
                    )
                }
            } else {
                Column(
                    modifier = Modifier.padding(
                        top = spacing.ExtraLarge,
                        start = spacing.ExtraLarge,
                        end = spacing.ExtraLarge,
                        bottom = spacing.Small
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(SynapseSpacing.Large)
                ) {
                    TextFieldColumn(
                        modifier = Modifier.fillMaxWidth(),
                        nameState = nameState,
                        descriptionState = descriptionState
                    )
                    ColorPicker(
                        modifier = Modifier.fillMaxWidth(),
                        colorPairings = colorPairings,
                        selectedColorSeed = selectedColorSeed,
                        onColorSelected = { selectedColorSeed = it }
                    )
                    CreateButton(onClick = {
                        onSave(
                            nameState.text.toString(),
                            descriptionState.text.toString(),
                            selectedColorSeed
                        )
                    })
                }
            }

        }
    }
}

@Composable
private fun CreateButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(horizontal = spacing.Large, vertical = spacing.ExtraLarge)
            .fillMaxWidth(0.6f)
            .height(72.dp),
        shape = RoundedCornerShape(SynapseSpacing.ExtraLarge),
        colors = ButtonColors(
            containerColor = colorScheme.tertiary,
            contentColor = colorScheme.onTertiary,
            disabledContainerColor = colorScheme.tertiaryContainer,
            disabledContentColor = colorScheme.onTertiaryContainer
        )
    ) {
        Text(
            "Create Deck",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@ExperimentalTextApi
@Composable
private fun TextFieldColumn(
    modifier: Modifier = Modifier,
    nameState: TextFieldState,
    descriptionState: TextFieldState
) {

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        unfocusedContainerColor = colorScheme.surfaceContainerLowest,
        focusedContainerColor = colorScheme.surfaceContainerLowest,
        focusedBorderColor = colorScheme.secondary,
        focusedLabelColor = colorScheme.secondary
    )

    val robotoFlexWide = FontFamily(
        Font(
            resId = R.font.roboto_flex,

            variationSettings = FontVariation.Settings(
                FontVariation.weight(950),
                FontVariation.width(130f)
            )
        )
    )

    Column(
        modifier = modifier.fillMaxWidth(0.8f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(SynapseSpacing.Large)
    ) {
        OutlinedTextField(
            state = nameState,
            label = { Text(text = "Name your deck") },
            modifier = Modifier
                .padding(top = spacing.Medium)
                .fillMaxWidth(),
            shape = RoundedCornerShape(spacing.Medium),
            colors = textFieldColors
        )
        OutlinedTextField(
            state = descriptionState,
            label = { Text(text = "What are we studying?") },
            modifier = Modifier
                .padding(top = spacing.ExtraSmall)
                .fillMaxWidth()
                .heightIn(min = 160.dp),
            shape = RoundedCornerShape(spacing.Medium),
            colors = textFieldColors
        )
    }
}

@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    colorPairings: List<ColorFamily>,
    selectedColorSeed: Int,
    onColorSelected: (Int) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Pick a vibe",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = spacing.Large).rotate(-2f),
            fontWeight = FontWeight(800),
            color = colorScheme.onSurface
        )

        FlowRow(
            modifier = Modifier.widthIn(max = 260.dp),
            horizontalArrangement = Arrangement.spacedBy(spacing.Large, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(spacing.Large),
            maxItemsInEachRow = 3
        ) {
            colorPairings.forEachIndexed { index, colorPairing ->
                VibeToken(
                    pairing = colorPairing,
                    isSelected = selectedColorSeed == index,
                    onClick = { onColorSelected(index) }
                )
            }
        }
    }
}

@Preview
@Composable
fun AddDeckDialogPreview() {
    SynapseTheme {
        AddDeckDialog()
    }
}
