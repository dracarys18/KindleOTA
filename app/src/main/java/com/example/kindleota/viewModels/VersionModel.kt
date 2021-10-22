package com.example.kindleota.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VersionModel : ViewModel() {
    //TODO: Create Live Data Observer for Version
    val version: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}