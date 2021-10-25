package com.example.kindleota.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
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
fun AddDeviceScreen(navController: NavController) {
    val application = LocalContext.current
    val dao = KindleDatabase.getInstance(application).kindledabaseDao
    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedtext by remember {
        mutableStateOf("")
    }
    var selectedind: Int by remember {
        mutableStateOf(0)
    }
    val context = LocalContext.current
    var textfieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
    Column {
        TopAppBar(
            title = { Text("Add a new Device") },
            navigationIcon = {
                IconButton(onClick = { navController.navigate(Screens.HomeScreen.route) }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                    )
                }
            }
        )
        Spacer(modifier = Modifier.padding(20.dp))
        Row {
            val labelColor =
                if (selectedtext.isEmpty()) {
                    Color.Gray
                } else {
                    MaterialTheme.colors.primary
                }

            ProvideTextStyle(
                value = if (isSystemInDarkTheme()) TextStyle(color = Color.White) else TextStyle(
                    color = Color.Black
                )
            ) {
                OutlinedTextField(value = selectedtext,
                    onValueChange = {
                        selectedtext = it
                    },
                    enabled = false,
                    label = {
                        (Text(text = "Kindle Variant", color = labelColor))
                    },
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .onGloballyPositioned { coordinates ->
                            textfieldSize = coordinates.size.toSize()
                        },
                    trailingIcon = {
                        Icon(
                            icon,
                            contentDescription = "DropDown Menu",
                            Modifier.clickable {
                                expanded = true
                            })
                    })
            }

            DropdownMenu(
                expanded = expanded, onDismissRequest = { expanded = false }, Modifier
                    .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
            ) {
                if (namelist.value.isEmpty()) {
                    DropdownMenuItem(onClick = { expanded = false }) {
                        LinearProgressIndicator()
                    }
                } else {
                    namelist.value.forEachIndexed { index, s ->
                        DropdownMenuItem(onClick = {
                            selectedtext = s
                            selectedind = index
                            expanded = false
                        }) {
                            Text(text = s)
                        }
                    }
                }
            }
        }
        var versiontext by remember {
            mutableStateOf("")
        }
        val localFocusManager = LocalFocusManager.current
        val focusRequester = FocusRequester()
        Spacer(modifier = Modifier.padding(20.dp))
        OutlinedTextField(
            value = versiontext,
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(70.dp)
                .focusRequester(focusRequester = focusRequester),
            onValueChange = { tftext ->
                versiontext = tftext
            },
            label = { Text("Version") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                localFocusManager.clearFocus()
            })
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Row {
            Spacer(modifier = Modifier.weight(1f, true))
            OutlinedButton(
                onClick = {
                    when {
                        isProper(versiontext, selectedtext) -> {
                            localFocusManager.clearFocus()
                            val data = KindleData(selectedind, selectedtext, versiontext)
                            val scope = CoroutineScope(Job() + Dispatchers.IO)
                            scope.launch {
                                dao.insertKindle(data)
                            }
                            navController.navigate(Screens.HomeScreen.route)
                        }
                        selectedtext.isEmpty() -> Toast.makeText(
                            context,
                            "Please Select Any Kindle",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        else -> Toast.makeText(
                            context,
                            "Please fill the version number properly",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondaryVariant),
                shape = RoundedCornerShape(50)
            ) {
                Icon(Icons.Filled.Done, tint = Color.Black, contentDescription = "Done")
            }
        }
    }
}

fun isProper(version: CharSequence, text: String): Boolean {
    val pattern = Regex("\\d+(\\.\\d+)+")
    return pattern.matches(version) && text.isNotEmpty()
}