package com.side.project.foodapp.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Kotlin Coroutines 模組化
object Coroutines {
    fun io(work: suspend (() -> Unit)) =
        CoroutineScope(Dispatchers.IO).launch { work() }

    fun main(work: suspend () -> Unit) =
        CoroutineScope(Dispatchers.Main).launch { work() }

    fun default(work: suspend () -> Unit) =
        CoroutineScope(Dispatchers.Default).launch { work() }

    fun unconfined(work: suspend () -> Unit) =
        CoroutineScope(Dispatchers.Unconfined).launch { work() }
}