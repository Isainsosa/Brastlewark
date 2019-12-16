package com.isain.brastlewark.ui.home

import com.isain.brastlewark.services.Services
import com.isain.brastlewark.services.responses.Inhabitant
import com.isain.network.Extensions.enqueue
import com.isain.network.Models.Result
import com.isainsosa.arco.interactor.BaseViewModel

class HomeViewModel: BaseViewModel<HomeAction, HomeState>(HomeState.Initial) {

    val inhabitants: MutableList<Inhabitant> = mutableListOf()
    var searchQuery: String = ""

    override fun react(action: HomeAction) {
        when(action) {
            is HomeAction.RetrieveCensusData -> retrieveCensusData()
            is HomeAction.SearchInhabitant -> searchInhabitant(action.searchQuery)
        }
    }

    override fun reduce(action: HomeAction): HomeState {
        return state
    }

    private fun retrieveCensusData() {
        if (inhabitants.isEmpty()) {
            update(HomeState.RetrievingCensusData)
            Services.censusDataService.retrieveCensusData().enqueue { result ->
                when(result) {
                    is Result.Success -> {
                        inhabitants.clear()
                        inhabitants.addAll(result.value.inhabitants)
                        update(HomeState.RetrieveCensusDataSuccess)
                    }
                    is Result.Failure -> {
                        update(HomeState.RetrieveCensusDataError)
                    }
                }
            }
        } else {
            update(HomeState.RetrieveCensusDataSuccess)
        }
    }

    private fun searchInhabitant(searchQuery: String) {
        this.searchQuery = searchQuery
        update(HomeState.SearchingInhabitant)
    }
}