package com.test.movemate.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.test.movemate.R
import com.test.movemate.ui.components.CategoryGrid
import com.test.movemate.ui.components.PackagingDropdown
import com.test.movemate.ui.viewmodel.CalculateViewModel

@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculateScreen(
    navigator: DestinationsNavigator,
    viewModel: CalculateViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var showAnimations by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { showAnimations = true }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Calculate", color = Color.White) },
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
    ) { inner ->
        LazyColumn(
            modifier = Modifier
                .padding(inner)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            item {
                Spacer(Modifier.height(24.dp))

                AnimatedVisibility(
                    visible = showAnimations,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(600)
                    )
                ) {
                    Text("Destination", style = MaterialTheme.typography.titleMedium)
                }

                Spacer(Modifier.height(16.dp))

                AnimatedVisibility(
                    visible = showAnimations,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(600)
                    )
                ) {
                    // Destination card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = if (isSystemInDarkTheme()) Color.Black else Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            val bg = Color(0xFFF8F8F8)
                            OutlinedTextField(
                                value = state.senderLocation,
                                onValueChange = viewModel::onSenderLocationChange,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(bg, shape = MaterialTheme.shapes.small)
                                    .height(56.dp),
                                leadingIcon = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            painterResource(id = R.drawable.send_icon),
                                            contentDescription = "Sender icon",
                                            tint = MaterialTheme.colorScheme.outlineVariant,
                                            modifier = Modifier.padding(start = 12.dp, end = 8.dp)
                                        )

                                        VerticalDivider(
                                            color = Color.LightGray,
                                            modifier = Modifier
                                                .height(24.dp)
                                                .width(1.dp)
                                        )
                                    }
                                },
                                placeholder = { Text("Sender location", color = Color.Gray) },
                                colors = OutlinedTextFieldDefaults.colors(
                                    disabledBorderColor   = Color.Transparent,
                                    focusedBorderColor    = Color.Transparent,
                                    unfocusedBorderColor  = Color.Transparent,
                                    disabledContainerColor= bg
                                ),
                                singleLine = true
                            )

                            Spacer(Modifier.height(16.dp))

                            OutlinedTextField(
                                value = state.receiverLocation,
                                onValueChange = viewModel::onReceiverLocationChange,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(bg, shape = MaterialTheme.shapes.small)
                                    .height(56.dp),
                                leadingIcon = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            painterResource(id = R.drawable.receive_icon),
                                            contentDescription = "Receiver icon",
                                            tint = MaterialTheme.colorScheme.outlineVariant,
                                            modifier = Modifier.padding(start = 12.dp, end = 8.dp)
                                        )
                                        VerticalDivider(
                                            color = Color.LightGray,
                                            modifier = Modifier
                                                .height(24.dp)
                                                .width(1.dp)
                                        )
                                    }
                                },
                                placeholder = { Text("Receiver location", color = Color.Gray) },
                                colors = OutlinedTextFieldDefaults.colors(
                                    disabledBorderColor   = Color.Transparent,
                                    focusedBorderColor    = Color.Transparent,
                                    unfocusedBorderColor  = Color.Transparent,
                                    disabledContainerColor= bg
                                ),
                                singleLine = true
                            )

                            Spacer(Modifier.height(16.dp))

                            OutlinedTextField(
                                value = state.weight,
                                onValueChange = viewModel::onWeightChange,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(bg, shape = MaterialTheme.shapes.small)
                                    .height(56.dp),
                                leadingIcon = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            painterResource(id = R.drawable.scale_icon),
                                            contentDescription = "Weight icon",
                                            tint = MaterialTheme.colorScheme.outlineVariant,
                                            modifier = Modifier.padding(start = 12.dp, end = 8.dp)
                                        )
                                        VerticalDivider(
                                            color = Color.LightGray,
                                            modifier = Modifier
                                                .height(24.dp)
                                                .width(1.dp)
                                        )
                                    }
                                },
                                placeholder = { Text("Approx weight", color = Color.Gray) },
                                colors = OutlinedTextFieldDefaults.colors(
                                    disabledBorderColor   = Color.Transparent,
                                    focusedBorderColor    = Color.Transparent,
                                    unfocusedBorderColor  = Color.Transparent,
                                    disabledContainerColor= bg
                                ),
                                singleLine = true
                            )

                            Spacer(Modifier.height(16.dp))
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))
            }

            item {
                AnimatedVisibility(visible = showAnimations) {
                    Column {

                        Text("Packaging", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "What are you sending?",
                            style = MaterialTheme.typography.bodyMedium
//                            copy(
//                                fontSize = 15.sp,
//                                color = MaterialTheme.colorScheme.outlineVariant
//                            )
                        )

                    }
                }
                Spacer(Modifier.height(8.dp))
                PackagingDropdown(
                    options = state.packagingOptions,
                    selectedOption = state.packagingOption,
                    onOptionSelected = viewModel::onPackagingOptionChange
                )
            }

            item {
                Spacer(Modifier.height(24.dp))
                AnimatedVisibility(visible = showAnimations) {
                    Column {
                        Text("Categories", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "What are you sending?",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                CategoryGrid(
                    items = state.categoryTags,
                    selectedItems = state.selectedItems,
                    onToggleItem = viewModel::onToggleCategory
                )
            }

            item {
                Spacer(Modifier.height(16.dp))
                AnimatedVisibility(visible = showAnimations) {
                    Button(
                        onClick = viewModel::calculate,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )

                    ) {
                        Text("Calculate")
                    }
                }
            }
        }
    }

    if (state.showTotalEstimateDialog && state.estimatedTotal != null) {
        TotalEstimateScreen(
            showDialog      = true,
            amount          = 1460,
            onDismissRequest= { viewModel.dismissDialog() },
            onGoBackHome    = {
                viewModel.dismissDialog()
                navigator.popBackStack()
            }
        )
    }
}