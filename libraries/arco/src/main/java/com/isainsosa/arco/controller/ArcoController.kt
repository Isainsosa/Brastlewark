package com.isainsosa.arco.controller

import com.isainsosa.arco.ActionWatcher
import com.isainsosa.arco.ArcoAction
import com.isainsosa.arco.ArcoState
import com.isainsosa.arco.renderer.ArcoRenderer

interface ArcoController<A : ArcoAction, S : ArcoState> {
    fun attachRenderer(renderer: ArcoRenderer<A, S>)
    fun addActionWatcher(actionWatcher: ActionWatcher<A>)

    fun dispatch(action: A)
    fun update(state: S)
}