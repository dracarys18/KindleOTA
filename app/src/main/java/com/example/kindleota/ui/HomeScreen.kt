package com.example.kindleota.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.kindleota.database.KindleData
import com.example.kindleota.database.KindleDatabase
import com.example.kindleota.navigation.Screens

@Composable
fun HomeScreen(navhostController: NavController) {
    val application = LocalContext.current
    val dao = KindleDatabase.getInstance(application).kindledabaseDao
    Scaffold(floatingActionButton = {
        ExtendedFloatingActionButton(
            text = { Text(text = "Add A Device", style = MaterialTheme.typography.button) },
            icon = { Icon(Icons.Filled.AddCircle, contentDescription = "Add Description") },
            onClick = {
                navhostController.navigate(Screens.AddDeviceScreen.route)
            })
    }, floatingActionButtonPosition = FabPosition.Center) {
        val kindles: List<KindleData> = dao.getallKindles()
        if (kindles.isEmpty()) {
            Text(text = "Nothing here")
        } else {
            LazyColumn {
                items(items = kindles) { kindle ->
                    KindleCards(name = kindle.kindleName!!, version = kindle.version!!)
                }

            }
        }
    }
}
