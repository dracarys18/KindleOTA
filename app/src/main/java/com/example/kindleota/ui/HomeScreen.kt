package com.example.kindleota.ui

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable

@Composable
fun addDevice() {
    Scaffold(floatingActionButton = {
        ExtendedFloatingActionButton(
            text = { Text(text = "Add A Device") },
            icon = { Icon(Icons.Filled.AddCircle, contentDescription = "Add Description") },
            onClick = { /*TODO*/ })
    }, floatingActionButtonPosition = FabPosition.Center) {
        Text(text = "Empty Af")
    }

}
