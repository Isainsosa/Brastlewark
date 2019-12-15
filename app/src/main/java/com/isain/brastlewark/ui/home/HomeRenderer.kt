package com.isain.brastlewark.ui.home

import android.view.View
import com.isainsosa.arco.renderer.ViewModelRenderer
import kotlinx.android.synthetic.main.view_home.view.*

class HomeRenderer(view: View, homeViewModel: HomeViewModel): ViewModelRenderer<HomeAction, HomeState, HomeViewModel>(view, homeViewModel) {

    override fun render(state: HomeState) {
        when(state) {
            is HomeState.RetrievingCensusData -> {
                view.progressBar.visibility = View.VISIBLE
            }
            is HomeState.RetrieveCensusDataSuccess -> {
                view.progressBar.visibility = View.GONE

            }
            is HomeState.RetrieveCensusDataError -> {
                view.progressBar.visibility = View.GONE
            }
        }
    }
}