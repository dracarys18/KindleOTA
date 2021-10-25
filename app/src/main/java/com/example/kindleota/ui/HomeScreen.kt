package com.example.kindleota.ui

import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.kindleota.database.KindleData
import com.example.kindleota.database.KindleDatabase
import com.example.kindleota.namelist
import com.example.kindleota.navigation.Screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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
        var kindles: List<KindleData> by remember { mutableStateOf(listOf()) }
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        scope.launch {
            kindles = dao.getallKindles()
        }
        if (kindles.isEmpty()) {
            Text(text = "Nothing here")
        } else {
            LazyColumn {
                items(items = kindles) { kindle ->
                    val latest = namelist.value.find { it.name == kindle.kindleName!! }
                    if (latest != null) {
                        KindleCards(
                            name = kindle.kindleName!!,
                            version = kindle.version!!,
                            latest_version = latest.version
                        )
                    } else {
                        Toast.makeText(application, "Loading Please Wait", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            }
        }
    }
}
