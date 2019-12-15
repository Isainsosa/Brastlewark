package com.isainsosa.arco.interactor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.isainsosa.arco.ArcoAction
import com.isainsosa.arco.ArcoState

abstract class BaseViewModel <A: ArcoAction, S: ArcoState>(initialState: S): ViewModel() {

    val liveState: MutableLiveData<S> = MutableLiveData()

    var state: S
        get() = liveState.value
            ?: throw UninitializedPropertyAccessException("You must initialize state in the constructor ${this::class.java.canonicalName}")
        private set(value) {
            liveState.value = value
        }

    init {
        liveState.value = initialState
    }

    /**
     * You should never call consume directly, always call dispatch(action) on the controller.
     */
    fun consume(action: A) {
        react(action)

        reduce(action).let { reducedState ->
            state = reducedState
        }
    }

    fun update(state: S) {
        liveState.postValue(state)
    }

    protected abstract fun react(action: A)
    protected abstract fun reduce(action: A): S
}