package com.olidev.synapse_mobile.ui.deck_details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.ButtonShapes
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.olidev.synapse_mobile.R
import com.olidev.synapse_mobile.ui.theme.SynapseSpacing
import com.olidev.synapse_mobile.ui.theme.SynapseTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun FlashCardListItem(
    front: String,
    back: String,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.secondary
) {
    val spacing = SynapseSpacing
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        onClick = onEdit
    ) {
        Column(
            modifier = Modifier.padding(spacing.Large)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = front,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(spacing.Medium))

            HorizontalDivider(
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.padding(spacing.Medium))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = back,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(spacing.Medium))

            ButtonGroup(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = color,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    onClick = onEdit,
                    shapes = ButtonShapes(
                        shape = MaterialTheme.shapes.extraLarge,
                        pressedShape = MaterialTheme.shapes.large
                    ),
                    content = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(spacing.Small)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.edit_24dp),
                                contentDescription = "Edit"
                            )
                            Text(text = "Edit")
                        }
                    }
                )
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = color.copy(alpha = 0.7f)
                    ),
                    onClick =  onDelete,
                    shapes = ButtonShapes(
                        shape = MaterialTheme.shapes.extraExtraLarge,
                        pressedShape = MaterialTheme.shapes.extraExtraLarge
                    ),
                    border = BorderStroke(width = 2.dp, color = color.copy(alpha = 0.7f)),
                    content = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(spacing.Small)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.delete_24dp),
                                contentDescription = "Delete"
                            )
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
@ExperimentalMaterial3ExpressiveApi
fun FlashCardListItemPreview() {
    SynapseTheme {
        FlashCardListItem(
            front = "this is the front of the card preview",
            back = "this is the text on the back",
            onEdit = {},
            onDelete = {}
        )
    }
}