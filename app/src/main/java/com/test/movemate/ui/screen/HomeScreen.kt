package com.test.movemate.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.test.movemate.R
import com.test.movemate.ui.components.AvailableVehicleCard
import com.test.movemate.ui.components.BottomNavBar
import com.test.movemate.ui.components.HomeTopBar
import com.test.movemate.ui.components.SearchBar
import com.test.movemate.ui.components.TrackingCard
import com.test.movemate.ui.screen.destinations.CalculateScreenDestination
import com.test.movemate.ui.screen.destinations.HomeScreenDestination
import com.test.movemate.ui.screen.destinations.SearchScreenDestination
import com.test.movemate.ui.screen.destinations.ShipmentScreenDestination
import com.test.movemate.ui.viewmodel.HomeViewModel


@Destination(start = true)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.homeData.collectAsState()
    val transitionState = remember {
        MutableTransitionState(false).apply { targetState = true }
    }

    Scaffold(
        bottomBar = {
            Box(Modifier.navigationBarsPadding()) {
                BottomNavBar(
                    currentRoute = "home",
                    onNavigate = { route ->
                        when (route) {
                            "home" -> navigator.navigate(HomeScreenDestination)
                            "calculate"    -> navigator.navigate(CalculateScreenDestination)
                        "shipment"   -> navigator.navigate(ShipmentScreenDestination)
//                        "profile"   -> navigator.navigate(ProfileScreenDestination)
                        }
                    }
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            AnimatedVisibility(
                visibleState = transitionState,
                enter = slideInHorizontally { -it } + fadeIn(),
                exit = fadeOut()
            ) {
                HomeTopBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_account_circle),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(MaterialTheme.shapes.small)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_location_icon),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Your Location",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = Color.White.copy(alpha = 0.8f)
                                        )
                                    )
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = state.locationLabel,
                                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(
                                        imageVector = Icons.Filled.KeyboardArrowDown,
                                        tint = Color.White,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    navActions = {
                        IconButton(onClick = { }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_notification_icon),
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                )
            }


            SearchBar(
                isClickable = true,
                onSearchClick = { navigator.navigate(SearchScreenDestination) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Tracking",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.padding(start = 16.dp)
            )

            TrackingCard(info = state.trackingInfo)

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Available Vehicles",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(start = 16.dp)
            ) {
                items(state.vehicles, key = { it.id }) { data ->
                    AvailableVehicleCard(
                        data = data,
                        modifier = Modifier.clickable { }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}