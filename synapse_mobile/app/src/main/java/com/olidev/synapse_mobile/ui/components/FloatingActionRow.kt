package com.olidev.synapse_mobile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.olidev.synapse_mobile.R
import com.olidev.synapse_mobile.ui.theme.SynapseSpacing

@Composable
fun FloatingActionRow(
    onNavigateBack: () -> Unit,
    isMoreShown: Boolean = false,
    onDeleteClick: () -> Unit = {},
    onEditClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onNavigateBack) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_back_24dp),
                contentDescription = "Back",
                modifier = Modifier.size(24.dp)
            )
        }

        if (isMoreShown) {
            var expanded by remember { mutableStateOf(false) }

            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.more_vert_24dp),
                        contentDescription = "Deck options",
                        modifier = Modifier.size(24.dp)
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    shape = RoundedCornerShape(SynapseSpacing.Large),
                    modifier = Modifier.padding(SynapseSpacing.Medium)
                ) {
                    DropdownMenuItem(
                        text = { Text("Edit Deck") },
                        leadingIcon = {
                            Icon(painterResource(id = R.drawable.edit_24dp), contentDescription = null)
                        },
                        onClick = {
                            expanded = false
                            onEditClick()
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Delete Deck", color = MaterialTheme.colorScheme.error) },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.delete_24dp),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )
                        },
                        onClick = {
                            expanded = false
                            onDeleteClick()
                        }
                    )
                }
            }
        }
    }
}