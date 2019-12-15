package com.isain.brastlewark.ui.home

import android.os.Bundle
import android.view.View
import com.isain.brastlewark.R
import com.isainsosa.arco.controller.BaseFragmentController
import com.isainsosa.arco.renderer.ViewModelRenderer

class HomeController:BaseFragmentController<HomeAction, HomeState, HomeViewModel>() {

    override val layoutId = R.layout.view_home

    override val viewModelClass = HomeViewModel::class.java

    override fun getArcoRenderer(view: View, viewModel: HomeViewModel) = HomeRenderer(view, viewModel)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arcoController.dispatch(HomeAction.RetrieveCensusData)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onAction(action: HomeAction) {
        super.onAction(action)

    }
}