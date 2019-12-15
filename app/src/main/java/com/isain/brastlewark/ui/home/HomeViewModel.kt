package com.isain.brastlewark.ui.home

import com.isain.brastlewark.services.Services
import com.isain.network.Extensions.enqueue
import com.isain.network.Models.Result
import com.isainsosa.arco.interactor.BaseViewModel

class HomeViewModel: BaseViewModel<HomeAction, HomeState>(HomeState.Initial) {

    override fun react(action: HomeAction) {
        when(action) {
            is HomeAction.RetrieveCensusData -> retrieveCensusData()
        }
    }

    override fun reduce(action: HomeAction): HomeState {
        return state
    }

    private fun retrieveCensusData() {
        update(HomeState.RetrievingCensusData)
        Services.censusDataService.retrieveCensusData().enqueue { result ->
           when(result) {
               is Result.Success -> {
                   println("HHTTPResponse: success")
                   update(HomeState.RetrieveCensusDataSuccess)
               }
               is Result.Failure -> {
                   println("HHTTPResponse: failure")
                   update(HomeState.RetrieveCensusDataError)
               }
           }
        }
    }
}