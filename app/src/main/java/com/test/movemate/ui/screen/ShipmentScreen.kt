package com.test.movemate.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.test.movemate.R
import com.test.movemate.domain.ShipmentData
import com.test.movemate.domain.ShipmentItem
import com.test.movemate.domain.ShipmentStatus
import com.test.movemate.ui.components.TabContent
import com.test.movemate.ui.viewmodel.ShipmentViewModel


@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShipmentScreen(
    navigator: DestinationsNavigator,
    viewModel: ShipmentViewModel = hiltViewModel()
) {
    // 1) Collect state from ViewModel
    val state by viewModel.uiState.collectAsState()

    // Offsets for animations
    val topBarOffset = remember { Animatable(-1000f) }
    val tabRowOffset = remember { Animatable(-1000f) }

    LaunchedEffect(Unit) {
        topBarOffset.animateTo(0f, tween(500))
        tabRowOffset.animateTo(0f, tween(300))
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Shipment History", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back_icon),
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { paddingValues ->
        // 2) Compute tab counts
        val shipments = state.shipments
        val allCount       = shipments.size
        val completedCount = shipments.count { it.status == ShipmentStatus.Completed }
        val inProgressCount= shipments.count { it.status == ShipmentStatus.InProgress }
        val pendingCount   = shipments.count { it.status == ShipmentStatus.Pending }
        val cancelledCount = shipments.count { it.status == ShipmentStatus.Canceled }

        val tabs = listOf(
            "All" to allCount,
            "Completed" to completedCount,
            "In progress" to inProgressCount,
            "Pending" to pendingCount,
            "Cancelled" to cancelledCount
        )

        var selectedTabIndex by remember { mutableStateOf(0) }

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // 3) Tab row with animated entry
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                edgePadding = 16.dp,
                containerColor = MaterialTheme.colorScheme.primary,
                indicator = { positions ->
                    TabRowDefaults.Indicator(
                        Modifier
                            .tabIndicatorOffset(positions[selectedTabIndex])
                            .fillMaxWidth(),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            ) {
                tabs.forEachIndexed { idx, (title, count) ->
                    Tab(
                        selected = selectedTabIndex == idx,
                        onClick = { selectedTabIndex = idx },
                        modifier = Modifier
                            .padding(10.dp)
                            .offset(x = -tabRowOffset.value.dp, y = tabRowOffset.value.dp)
                    ) {
                        TabContent(title, count, selectedTabIndex == idx)
                    }
                }
            }


            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                val filtered = when (selectedTabIndex) {
                    0 -> shipments
                    1 -> shipments.filter { it.status == ShipmentStatus.Completed }
                    2 -> shipments.filter { it.status == ShipmentStatus.InProgress }
                    3 -> shipments.filter { it.status == ShipmentStatus.Pending }
                    4 -> shipments.filter { it.status == ShipmentStatus.Canceled }
                    else -> emptyList()
                }

                item {
                    AnimatedVisibility(
                        visible = true,
                        enter = slideInVertically(tween(600))
                    ) {
                        Text(
                            "Shipments",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }

                items(filtered) { shipment ->
                    AnimatedShipmentItem(shipmentData = shipment)
                }
            }


        }
    }
}

@Composable
fun AnimatedShipmentItem(
    shipmentData: ShipmentData,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }

    // trigger the animation on first composition
    LaunchedEffect(Unit) {
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
        ),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
        )
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor =if (isSystemInDarkTheme()) Color.Black else Color.White),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 50.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .background(
                                color = Color.Gray.copy(alpha = 0.08f),
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(
                                id = when (shipmentData.status) {
                                    ShipmentStatus.Loading -> R.drawable.ic_loading_icon
                                    ShipmentStatus.InProgress -> R.drawable.ic_in_progress_icon
                                    ShipmentStatus.Pending -> R.drawable.ic_shipment_history_icon
                                    ShipmentStatus.Completed -> R.drawable.ic_complete_icon
                                    ShipmentStatus.Canceled -> R.drawable.ic_cancelled_icon
                                }
                            ),
                            tint = when (shipmentData.status) {
                                ShipmentStatus.Loading -> Color(0xFF0277BD)
                                ShipmentStatus.InProgress -> Color(0xFF77CCA4)
                                ShipmentStatus.Pending -> Color(0xFFDA955D)
                                ShipmentStatus.Completed -> Color.Green
                                ShipmentStatus.Canceled -> Color.Red
                            },
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = shipmentData.status.data,
                            color = when (shipmentData.status) {
                                ShipmentStatus.Loading -> Color(0xFF0277BD)
                                ShipmentStatus.InProgress -> Color(0xFF77CCA4)
                                ShipmentStatus.Pending -> Color(0xFFDA955D)
                                ShipmentStatus.Completed -> Color.Green
                                ShipmentStatus.Canceled -> Color.Red
                            },
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        shipmentData.title,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        shipmentData.description,
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.outlineVariant)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row {
                        Text(
                            text = shipmentData.amount,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = if (isSystemInDarkTheme()) Color.White else MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "• ${shipmentData.date}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = if (isSystemInDarkTheme()) Color.White else  MaterialTheme.typography.bodyMedium.color.copy(alpha = 0.7f)
                            )
                        )
                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.grey_box),
                    contentDescription = "Grey Box",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(end = 8.dp)
                )
            }
        }
    }
}