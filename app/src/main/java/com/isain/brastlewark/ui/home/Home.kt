package com.isain.brastlewark.ui.home

import com.isain.brastlewark.services.responses.Inhabitant
import com.isainsosa.arco.ArcoAction
import com.isainsosa.arco.ArcoState

sealed class HomeState: ArcoState {
    object Initial: HomeState()
    object RetrievingCensusData: HomeState()
    object RetrieveCensusDataSuccess: HomeState()
    object RetrieveCensusDataError: HomeState()
    object SearchingInhabitant: HomeState()
}

sealed class HomeAction: ArcoAction {
    object RetrieveCensusData: HomeAction()
    class SearchInhabitant(val searchQuery: String): HomeAction()
    class InhabitantClicked(val inhabitant: Inhabitant): HomeAction()
}