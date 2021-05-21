package com.vishnu.routesapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

open class BaseViewModel : ViewModel() {

    fun launch(block: suspend CoroutineScope.() -> Unit): Job {

        return viewModelScope.launch { block() }
    }
}
