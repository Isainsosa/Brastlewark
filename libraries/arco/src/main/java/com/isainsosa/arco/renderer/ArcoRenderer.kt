package com.isainsosa.arco.renderer

import android.os.Bundle
import com.isainsosa.arco.ArcoAction
import com.isainsosa.arco.ArcoState
import com.isainsosa.arco.controller.ArcoController

interface ArcoRenderer<A : ArcoAction, S : ArcoState> {
    fun render(state: S)

    fun attach(controller: ArcoController<A, S>)

    fun onSavedInstanceState(outState: Bundle) {}
    fun restore(savedInstanceState: Bundle?) {}
}