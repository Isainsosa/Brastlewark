package com.isain.brastlewark.ui.home

import com.isainsosa.arco.interactor.BaseViewModel

class HomeViewModel: BaseViewModel<HomeAction, HomeState>(HomeState.Initial) {

    override fun react(action: HomeAction) {

    }

    override fun reduce(action: HomeAction): HomeState {
        return state
    }
}