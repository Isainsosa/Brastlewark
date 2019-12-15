package com.isainsosa.arco

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

// Dispatches execution onto the Android main UI thread
var uiContext: CoroutineContext = Dispatchers.Main

// Represents a common pool of shared threads as the coroutine dispatcher
val bgContext: CoroutineContext = Dispatchers.IO