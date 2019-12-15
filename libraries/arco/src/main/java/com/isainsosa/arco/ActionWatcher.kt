package com.isainsosa.arco

interface ActionWatcher<in A: ArcoAction> {
    fun onAction(action: A)
}