package com.test.movemate.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.test.movemate.domain.VehicleOption
import androidx.compose.material3.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp


@Composable
fun AvailableVehicleCard(
    data: VehicleOption,
    modifier: Modifier = Modifier
) {
    val transitionState = remember { MutableTransitionState(false).apply { targetState = true } }

    Card(
        modifier = Modifier
            .height(200.dp)
            .width(135.dp),
        colors = CardDefaults.cardColors(containerColor = if (isSystemInDarkTheme()) Color.Black else Color.White),

    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(modifier = Modifier.align(Alignment.BottomEnd)) {
                AnimatedVisibility(
                    visibleState = transitionState,
                    enter = slideInHorizontally(initialOffsetX = { -it }) + fadeIn(),
                    exit = fadeOut()
                ) {
                    Image(
                        painter = painterResource(id = data.image),
                        contentDescription = data.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(180.dp)

                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.padding(6.dp)) {
                Text(
                    text = data.title,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
                )

                Text(
                    text = data.subtitle,
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.outlineVariant)
                )
            }
        }
    }

}