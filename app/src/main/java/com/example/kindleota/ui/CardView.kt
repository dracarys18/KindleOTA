package com.example.kindleota.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.kindleota.database.KindleDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun KindleCards(name: String, version: String) {
    val application = LocalContext.current
    val dao = KindleDatabase.getInstance(application).kindledabaseDao
    Card(
        shape = MaterialTheme.shapes.small,
        elevation = 10.dp, modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
            .wrapContentHeight()
    ) {
        Column {
            Text(
                text = "Kindle Name",
                modifier = Modifier.padding(start = 15.dp, top = 15.dp),
            )
            Text(text = name, modifier = Modifier.padding(start = 20.dp))
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = "Firmware Version",
                modifier = Modifier.padding(start = 15.dp, top = 15.dp),
            )
            Text(text = version, modifier = Modifier.padding(start = 20.dp))
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.weight(1f, true))
                IconButton(
                    onClick = {
                        val scope = CoroutineScope(Job() + Dispatchers.IO)
                        scope.launch {
                            dao.deleteKindle(name)
                        }
                    }
                ) {
                    Icon(Icons.Rounded.Delete, contentDescription = "Delete the Entry")
                }
            }
        }
    }
}
