package com.isainsosa.arco.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.isainsosa.arco.ActionWatcher
import com.isainsosa.arco.ArcoAction
import com.isainsosa.arco.ArcoState
import com.isainsosa.arco.interactor.BaseViewModel
import com.isainsosa.arco.renderer.ViewModelRenderer

abstract class BaseFragmentController<A: ArcoAction, S: ArcoState, VM: BaseViewModel<A, S>>(bundle: Bundle? = null, private val arcoComponent: ViewModelController<A, S>? = null): Fragment(), ActionWatcher<A> {

    constructor(bundle: Bundle?) : this(bundle, null)

    constructor(arcoComponent: ViewModelController<A, S>?) : this(null, arcoComponent)

    init {
        arguments = bundle
    }

    private val viewModelObserver = Observer<S> {
        arcoController.updateRenderers(it)
    }
    abstract val viewModelClass: Class<VM>
    abstract val layoutId: Int
    open var viewModelFactory: ViewModelProvider.Factory? = null

    lateinit var viewModel: VM
        private set
    lateinit var arcoController: ViewModelController<A, S>
        private set
    lateinit var arcoRenderer: ViewModelRenderer<A, S, VM>
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =  ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
        arcoController = arcoComponent ?: getArcoComponent(viewModel)
        viewModel.liveState.observe(this, viewModelObserver)
        arcoController.addActionWatcher(this)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(layoutId, container, false)
        loadRenderer(view)
        return view
    }

    internal fun loadRenderer(view: View) {
        arcoRenderer = getArcoRenderer(view, viewModel)
        arcoController.attachRenderer(arcoRenderer)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arcoController.restoreRenderers(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        arcoController.saveRenderersInstanceState(outState)
    }

    override fun onAction(action: A) {

    }

    abstract fun getArcoRenderer(view: View, viewModel: VM): ViewModelRenderer<A, S, VM>
    open fun getArcoComponent(viewModel: VM): ViewModelController<A, S> = ViewModelController(viewModel)
}