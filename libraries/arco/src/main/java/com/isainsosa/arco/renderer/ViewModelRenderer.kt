package com.isainsosa.arco.renderer

import android.content.Context
import android.view.View
import com.isainsosa.arco.ArcoAction
import com.isainsosa.arco.ArcoState
import com.isainsosa.arco.controller.ArcoController
import com.isainsosa.arco.interactor.BaseViewModel

abstract class ViewModelRenderer<A : ArcoAction, S : ArcoState, VM : BaseViewModel<A, S>>(val view: View, val viewModel: VM) : ArcoRenderer<A, S> {

    protected lateinit var controller: ArcoController<A, S>

    val context: Context = view.context

    override fun attach(controller: ArcoController<A, S>) {
        this.controller = controller
    }
}