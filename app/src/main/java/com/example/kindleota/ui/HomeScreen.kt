package com.example.kindleota.ui

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.kindleota.navigation.Screens

@Composable
fun HomeScreen(navhostController: NavController) {
    Scaffold(floatingActionButton = {
        ExtendedFloatingActionButton(
            text = { Text(text = "Add A Device", style = MaterialTheme.typography.button) },
            icon = { Icon(Icons.Filled.AddCircle, contentDescription = "Add Description") },
            onClick = {
                navhostController.navigate(Screens.AddDeviceScreen.route)
            })
    }, floatingActionButtonPosition = FabPosition.Center) {
        KindleCards(name = "Kindle Oasis 10th Generation", version = "5.10.10")
    }

}
