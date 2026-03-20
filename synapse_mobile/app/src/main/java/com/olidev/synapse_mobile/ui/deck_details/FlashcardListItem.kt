package com.olidev.synapse_mobile.ui.deck_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
    modifier: Modifier = Modifier,
) {
    val spacing = SynapseSpacing
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        onClick = onEdit
    ) {
        Column(
            modifier = Modifier.padding(spacing.Large)
        ) {
            Text(
                text = front,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.padding(spacing.Medium))

            HorizontalDivider(
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.padding(spacing.Medium))

            Text(
                text = back,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.padding(spacing.Medium))

            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(id = R.drawable.edit_24dp),
                    contentDescription = "Edit",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
@Preview
@Composable
@ExperimentalMaterial3ExpressiveApi
fun FlashCardListItemPreview(){
    SynapseTheme{
        FlashCardListItem(
            front = "this is the front of the card preview",
            back = "this is the text on the back",
            onEdit = {}
        )
    }
}