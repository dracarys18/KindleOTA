package com.example.kindleota.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SecurityUpdate
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.*
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
fun KindleCards(name: String, version: String, latest_version: String, download_uri: String) {
    val application = LocalContext.current
    val dao = KindleDatabase.getInstance(application).kindledabaseDao
    var confirmpressed by remember {
        mutableStateOf(false)
    }
    val uriintent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(download_uri)) }
    if (confirmpressed) {
        AlertDialog(
            onDismissRequest = {
                confirmpressed = false
            },
            confirmButton = {
                Button(onClick = {
                    confirmpressed = false
                    val scope = CoroutineScope(Job() + Dispatchers.IO)
                    scope.launch {
                        dao.deleteKindle(name)
                    }
                }) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    confirmpressed = false
                }) {
                    Text(text = "Cancel")
                }
            },
            title = {
                Text(text = "Do you wanna stop tracking $name")
            },
            text = {
                Text("Pleas press confirm to stop tracking")
            },
        )
    }
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
            Spacer(modifier = Modifier.padding(5.dp))
            Text(text = name, modifier = Modifier.padding(start = 20.dp))
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = "Current Version",
                modifier = Modifier.padding(start = 15.dp, top = 15.dp),
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text(text = version, modifier = Modifier.padding(start = 20.dp))
            Text(
                text = "Latest Version",
                modifier = Modifier.padding(start = 15.dp, top = 15.dp),
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text(text = latest_version, modifier = Modifier.padding(start = 20.dp))
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (version < latest_version) {
                    IconButton(onClick = {
                        application.startActivity(uriintent)
                    }) {
                        Spacer(modifier = Modifier.padding(bottom = 5.dp))
                        Row {
                            Icon(
                                Icons.Filled.SecurityUpdate,
                                contentDescription = "Update the Kindle"
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f, true))
                IconButton(
                    onClick = { confirmpressed = true }
                ) {
                    Icon(Icons.Rounded.Delete, contentDescription = "Delete the Entry")
                }
            }
        }
    }
}
