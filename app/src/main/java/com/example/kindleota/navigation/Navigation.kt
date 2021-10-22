package com.example.kindleota.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kindleota.ui.AddDeviceScreen
import com.example.kindleota.ui.HomeScreen

@Composable
fun Navigation() {
    val navhostcontroller = rememberNavController()
    NavHost(navController = navhostcontroller, startDestination = Screens.HomeScreen.route) {
        composable(route = Screens.HomeScreen.route) {
            HomeScreen(navhostController = navhostcontroller)
        }
        composable(route = Screens.AddDeviceScreen.route) {
            AddDeviceScreen(navController = navhostcontroller)
        }
    }

}
