package com.test.movemate.ui.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.test.movemate.R
import kotlinx.coroutines.delay


@Composable
fun TotalEstimateScreen(
    showDialog: Boolean,
    amount: Int,
    onDismissRequest: () -> Unit,
    onGoBackHome: () -> Unit
) {
    if (!showDialog) return

    val slideOffset = remember { Animatable(initialValue = 500f) }
    val fadeAlpha = remember { Animatable(initialValue = 0f) }

    var displayedAmount by remember { mutableIntStateOf(1200) }

    LaunchedEffect(showDialog, amount) {
        slideOffset.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 600, easing = LinearOutSlowInEasing)
        )
        fadeAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 600, easing = LinearOutSlowInEasing)
        )

        if (amount > 0) {
            for (i in displayedAmount..amount) {
                displayedAmount = i
                delay(5)
            }
        }
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_movemate_icon),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(220.dp)
                        .offset(y = slideOffset.value.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.grey_box),
                    contentDescription = "Box",
                    modifier = Modifier
                        .size(100.dp)
                        .offset(y = slideOffset.value.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
                // Title
                Text(
                    text = "Total Estimated Amount",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(fadeAlpha.value)
                        .offset(y = slideOffset.value.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$ $displayedAmount",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color(0xFF77CCA4),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(fadeAlpha.value)
                        .offset(y = slideOffset.value.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "This amount is estimated and will vary if you change location or weight",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .alpha(fadeAlpha.value)
                        .offset(y = slideOffset.value.dp),
                )
                Spacer(modifier = Modifier.height(24.dp))
                // Button
                Button(
                    onClick = onGoBackHome,
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(fadeAlpha.value)
                        .padding(vertical = 16.dp)
                        .offset(y = slideOffset.value.dp),
                    shape = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Text(
                        text = "Back to Home",
                        modifier = Modifier.padding(5.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
                    )
                }
            }
        }
    }
}