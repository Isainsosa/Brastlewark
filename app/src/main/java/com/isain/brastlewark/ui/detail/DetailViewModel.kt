package com.isain.brastlewark.ui.detail

import com.isain.brastlewark.App
import com.isain.brastlewark.database.entities.Inhabitant
import com.isainsosa.arco.bgContext
import com.isainsosa.arco.interactor.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DetailViewModel: BaseViewModel<DetailAction, DetailState>(DetailState.Initial) {
    var inhabitant: Inhabitant? = null
    override fun react(action: DetailAction) {
        when(action) {
            is DetailAction.GetDetail -> getInhabitantDetail(action.inhabitantId)
        }
    }

    override fun reduce(action: DetailAction): DetailState {
        return state
    }

    private fun getInhabitantDetail(inhabitantId: Long) {
        CoroutineScope(bgContext).launch {
            inhabitant = App.database.inhabitantDao().getInhabitant(inhabitantId)
            update(DetailState.ShowDetail)
        }
    }
}