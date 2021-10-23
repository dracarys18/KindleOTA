package com.example.kindleota.ui

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
        var kindles: Array<KindleData> = dao.getallKindles()
        if (kindles.isEmpty()) {
            Text(text = "Nothing here")
        } else {
            kindles.forEach {
                KindleCards(name = it.kindleName!!, version = it.version!!)
            }
        }
    }
}
