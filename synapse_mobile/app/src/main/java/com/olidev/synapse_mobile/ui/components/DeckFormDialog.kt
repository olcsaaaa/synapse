package com.olidev.synapse_mobile.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.olidev.synapse_mobile.R
import com.olidev.synapse_mobile.ui.theme.ColorFamily
import com.olidev.synapse_mobile.ui.theme.SynapseSpacing
import com.olidev.synapse_mobile.ui.theme.SynapseTheme
import com.olidev.synapse_mobile.ui.theme.Typography

val spacing = SynapseSpacing

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalTextApi::class
)
@Composable
fun DeckFormDialog(
    modifier: Modifier = Modifier,
    initialName: String = "",
    initialDescription: String = "",
    initialColorSeed: Int = 0,
    isEditing: Boolean = false,
    onDismiss: () -> Unit = {},
    onSave: (name: String, description: String, colorSeed: Int) -> Unit
) {
    val nameState = rememberTextFieldState(initialText = initialName)
    val descriptionState = rememberTextFieldState(initialText = initialDescription)
    var selectedColorSeed by remember { mutableIntStateOf(initialColorSeed) }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
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

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.fillMaxWidth(0.75f)
    ) {
        Surface(
            modifier = modifier
                .widthIn(max = 600.dp)
                .padding(SynapseSpacing.Large),
            shape = RoundedCornerShape(SynapseSpacing.Hero),
            color = colorScheme.surfaceContainerLow,
            tonalElevation = 2.dp,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Surface(
                            modifier = Modifier
                                .size(110.dp)
                                .rotate(-12f),
                            shape = MaterialShapes.Sunny.toShape(),
                            color = colorPairings[selectedColorSeed].color.copy(alpha = 0.2f)
                        ) {}

                        Surface(
                            modifier = Modifier
                                .size(90.dp)
                                .rotate(5f),
                            shape = MaterialShapes.Sunny.toShape(),
                            color = colorPairings[selectedColorSeed].color,
                            shadowElevation = 8.dp
                        ) {
                            Icon(
                                painter = painterResource(if (isEditing) R.drawable.edit_24dp else R.drawable.round_add_24),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(24.dp)
                                    .rotate(-5f),
                                tint = colorPairings[selectedColorSeed].onColor
                            )
                        }
                    }

                    Text(
                        text = if (isEditing) "Edit Deck" else "New Deck",
                        style = Typography.headlineSmall,
                        color = colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )

                    BoxWithConstraints(
                        modifier = Modifier
                            .padding(spacing.ExtraLarge)
                    ) {
                        val isHorizontal = maxWidth > 600.dp

                        if (isHorizontal) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(spacing.ExtraLarge),
                                verticalAlignment = Alignment.Top
                            ) {
                                TextFieldColumn(
                                    modifier = Modifier.weight(1.5f),
                                    nameState = nameState,
                                    descriptionState = descriptionState,
                                    chosenColor = colorPairings[selectedColorSeed]
                                )
                                ColorPicker(
                                    modifier = Modifier.weight(1f),
                                    colorPairings = colorPairings,
                                    selectedColorSeed = selectedColorSeed,
                                    onColorSelected = { selectedColorSeed = it }
                                )
                            }
                        } else {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(spacing.Large),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                TextFieldColumn(
                                    nameState = nameState,
                                    descriptionState = descriptionState,
                                    chosenColor = colorPairings[selectedColorSeed]
                                )
                                ColorPicker(
                                    colorPairings = colorPairings,
                                    selectedColorSeed = selectedColorSeed,
                                    onColorSelected = { selectedColorSeed = it }
                                )
                            }
                        }
                    }

                    CreateButton(
                        text = if (isEditing) "Save Changes" else "Create Deck",
                        containerColor = colorPairings[selectedColorSeed].color,
                        contentColor = colorPairings[selectedColorSeed].onColor,
                        onClick = {
                            onSave(
                                nameState.text.toString(),
                                descriptionState.text.toString(),
                                selectedColorSeed
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(spacing.Medium))
                }
            }
        }
    }
}

@Composable
private fun CreateButton(
    text: String,
    containerColor: Color,
    contentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(horizontal = spacing.Large, vertical = spacing.Medium)
            .fillMaxWidth(0.7f)
            .height(64.dp),
        shape = RoundedCornerShape(SynapseSpacing.Large),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Text(
            text,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@ExperimentalTextApi
@Composable
fun TextFieldColumn(
    modifier: Modifier = Modifier,
    nameState: TextFieldState,
    descriptionState: TextFieldState,
    chosenColor: ColorFamily = ColorFamily(
        colorScheme.secondary,
        colorScheme.onSecondary,
        colorScheme.secondaryContainer,
        colorScheme.onSecondaryContainer
    )
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        unfocusedContainerColor = colorScheme.surfaceContainerLowest,
        focusedContainerColor = colorScheme.surfaceContainerLowest,
        focusedBorderColor = chosenColor.color,
        focusedLabelColor = chosenColor.color,
        cursorColor = chosenColor.color,
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
            colors = textFieldColors,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        OutlinedTextField(
            state = descriptionState,
            label = { Text(text = "What are we studying?") },
            modifier = Modifier
                .padding(top = spacing.ExtraSmall)
                .fillMaxWidth()
                .heightIn(min = 160.dp),
            shape = RoundedCornerShape(spacing.Medium),
            colors = textFieldColors,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            onKeyboardAction = {
                focusManager.clearFocus()
                keyboardController?.hide()
            }
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
            modifier = Modifier
                .padding(bottom = spacing.Large)
                .rotate(-2f),
            fontWeight = FontWeight(800),
            color = colorScheme.onSurface
        )

        FlowRow(
            modifier = Modifier.widthIn(max = 260.dp),
            horizontalArrangement = Arrangement.spacedBy(
                spacing.Large,
                Alignment.CenterHorizontally
            ),
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
fun DeckFormDialogPreview() {
    SynapseTheme {
        DeckFormDialog(onSave = { _, _, _ -> })
    }
}