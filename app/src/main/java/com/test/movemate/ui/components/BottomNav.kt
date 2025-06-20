package com.test.movemate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.test.movemate.ui.screen.NavGraphs
import com.test.movemate.ui.screen.destinations.HomeScreenDestination


private data class TabItem(val route: String, val icon: @Composable () -> Unit, val label: String)

private val tabs = listOf(
    TabItem("home",     { Icon(Icons.Default.Home,     contentDescription = "Home") },      "Home"),
    TabItem("calculate",{ Icon(Icons.Default.Calculate,contentDescription = "Calculate") },"Calculate"),
    TabItem("shipment",  { Icon(Icons.Default.History,  contentDescription = "Shipment") }, "Shipment"),
    TabItem("profile",  { Icon(Icons.Default.Person,   contentDescription = "Profile") },   "Profile")
)

@Composable
fun BottomNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    Surface(

        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
    ) {
        Row(Modifier.height(64.dp)) {
            tabs.forEach { tab ->
                val selected = tab.route == currentRoute
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { onNavigate(tab.route) },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        Modifier
                            .height(3.dp)
                            .fillMaxWidth()
                            .background(if (selected) MaterialTheme.colors.primary else Color.Transparent)
                    )
                    Spacer(Modifier.height(6.dp))
                    val tint = if (selected) MaterialTheme.colors.primary else Color.Gray
                    CompositionLocalProvider(LocalContentColor provides tint) {
                        tab.icon()
                    }
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = tab.label,
                        fontSize = 12.sp,
                        color = tint
                    )
                }
            }
        }
    }
}
