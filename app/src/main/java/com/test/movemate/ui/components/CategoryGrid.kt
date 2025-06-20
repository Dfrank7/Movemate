package com.test.movemate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.movemate.R
import com.test.movemate.domain.CategoryTag

@Composable
fun CategoryGrid(
    items: List<CategoryTag>,
    selectedItems: Set<CategoryTag>,
    onToggleItem: (CategoryTag) -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalArrangement   = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
    ) {
        items.forEach { tag ->
            CategoryTagBubble(
                item = tag,
                isSelected = selectedItems.contains(tag),
                onToggleItem = onToggleItem
            )
        }
    }
}

@Composable
fun CategoryTagBubble(
    item: CategoryTag,
    isSelected: Boolean,
    onToggleItem: (CategoryTag) -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = if (isSelected) Color(0xFF132333) else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.15f
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onToggleItem(item) }
            .padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isSelected) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_done_icon),
                    modifier = Modifier.size(16.dp),
                    contentDescription = null,
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            Text(
                text = item.label,
                fontSize = 14.sp,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}