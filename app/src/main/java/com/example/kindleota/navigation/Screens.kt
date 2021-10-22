package com.example.kindleota.navigation

sealed class Screens(val route:String){
    object HomeScreen : Screens("home_screen")
    object AddDeviceScreen : Screens("add_screen")
}
