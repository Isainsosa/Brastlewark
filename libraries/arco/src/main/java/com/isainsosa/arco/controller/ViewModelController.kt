package com.isainsosa.arco.controller

import android.os.Bundle
import com.isainsosa.arco.ActionWatcher
import com.isainsosa.arco.ArcoAction
import com.isainsosa.arco.ArcoState
import com.isainsosa.arco.interactor.BaseViewModel
import com.isainsosa.arco.renderer.ArcoRenderer
import com.isainsosa.arco.uiContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ViewModelController<A : ArcoAction, S : ArcoState>(val viewModel: BaseViewModel<A, S>,
                                                         actionWatcher: ActionWatcher<A>? = null) : ArcoController<A, S> {

    private val renderers: MutableList<ArcoRenderer<A, S>> = mutableListOf()

    private val actionWatchers: MutableList<ActionWatcher<A>> = mutableListOf()

    init {
        actionWatcher?.let { actionWatchers.add(actionWatcher) }
    }

    /**
     * Post a Runnable to the main thread that will dispatch the action
     * This will notify all ActionWatchers, ActionMappers and the Interactor on the MainThread.
     */
    override fun dispatch(action: A) {
        CoroutineScope(uiContext).launch { dispatchAction(action) }
    }

    private fun dispatchAction(action: A) {
        val currentAction: A? = action

        currentAction?.let {
            viewModel.consume(it)

            actionWatchers.forEach { it.onAction(action) }
        }
    }

    override fun update(state: S) {
        viewModel.liveState.postValue(state)
    }

    override fun attachRenderer(renderer: ArcoRenderer<A, S>) {
        if (!renderers.contains(renderer)) {
            renderers.add(renderer)

            renderer.attach(this)
        }
    }

    override fun addActionWatcher(actionWatcher: ActionWatcher<A>) {
        actionWatchers.add(actionWatcher)
    }

    fun updateRenderers(state: S) {
        renderers.forEach { it.render(state) }
    }

    fun restoreRenderers(savedInstanceState: Bundle?) {
        renderers.forEach{ it.restore(savedInstanceState)}
    }

    fun saveRenderersInstanceState(outState: Bundle) {
        renderers.forEach{ it.onSavedInstanceState(outState)}
    }
}