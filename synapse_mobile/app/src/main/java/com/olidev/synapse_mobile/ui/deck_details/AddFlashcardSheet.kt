package com.olidev.synapse_mobile.ui.deck_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.olidev.synapse_mobile.ui.theme.ColorFamily
import com.olidev.synapse_mobile.ui.theme.SynapseSpacing
import com.olidev.synapse_mobile.ui.theme.SynapseTheme
import com.olidev.synapse_mobile.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFlashCardSheet(
    onDismiss: () -> Unit,
    onSave: (front: String, back: String) -> Unit,
    colorSeed: Int,
    initialFront : String = "",
    initialBack : String = ""
) {
    val frontState = rememberTextFieldState(initialFront)
    val backState = rememberTextFieldState(initialBack)

    val extra = SynapseTheme.extraColors
    val colors = MaterialTheme.colorScheme


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

    val selectedColorPairing = colorPairings.getOrElse(colorSeed){colorPairings[0]}

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        dragHandle = { BottomSheetDefaults.DragHandle(color = selectedColorPairing.color.copy(alpha = 0.5f)) }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = SynapseSpacing.Large)
                .padding(bottom = SynapseSpacing.ExtraLarge)
                .fillMaxWidth()
                .widthIn(max = 600.dp),
            verticalArrangement = Arrangement.spacedBy(SynapseSpacing.Medium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "The Flashcard",
                style = Typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            FlashcardInputArea(
                state = frontState,
                label ="Front (The Question)",
                accentColor = selectedColorPairing.color
                )
            FlashcardInputArea(
                state = backState,
                label ="Back (The Answer)",
                accentColor = selectedColorPairing.color
                )

            Button(
                onClick = {
                    onSave(
                        frontState.text.toString(),
                        backState.text.toString()
                    )
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth().height(SynapseSpacing.Hero),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(containerColor = selectedColorPairing.color, contentColor = selectedColorPairing.onColor)
                ) {
                Text("Add to Deck", style = Typography.titleMedium)
            }
        }
    }
}

@Preview
@Composable
@ExperimentalMaterial3Api
fun AddFlashcardSheetPreview(){
    SynapseTheme {
        AddFlashCardSheet(onDismiss = {}, onSave = {_,_ ->}, colorSeed = 4)
    }
}
