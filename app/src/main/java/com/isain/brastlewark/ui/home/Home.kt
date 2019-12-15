package com.isain.brastlewark.ui.home

import com.isainsosa.arco.ArcoAction
import com.isainsosa.arco.ArcoState

sealed class HomeState: ArcoState {
    object Initial: HomeState()
    object RetrievingCensusData: HomeState()
    object RetrieveCensusDataSuccess: HomeState()
    object RetrieveCensusDataError: HomeState()
}

sealed class HomeAction: ArcoAction {
    object RetrieveCensusData: HomeAction()
}