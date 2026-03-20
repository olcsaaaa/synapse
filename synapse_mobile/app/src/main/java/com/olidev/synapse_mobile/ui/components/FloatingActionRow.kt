package com.olidev.synapse_mobile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.olidev.synapse_mobile.R

@Composable
fun FloatingActionRow(
    onNavigateBack: () -> Unit,
    isMoreShown : Boolean = false,
    onMoreClicked : ()-> Unit = {}
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

        if(isMoreShown){
            IconButton(onClick = { onMoreClicked }) {
                Icon(
                    painter = painterResource(id = R.drawable.more_vert_24dp),
                    contentDescription = "More options",
                    modifier = Modifier.size(24.dp)
                )
            }
        }



    }
}