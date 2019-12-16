package com.isain.brastlewark.ui.detail

import com.isainsosa.arco.ArcoAction
import com.isainsosa.arco.ArcoState

sealed class DetailState: ArcoState {
    object Initial: DetailState()
    object ShowDetail: DetailState()
}

sealed class DetailAction: ArcoAction {
    class GetDetail(val inhabitantId: Long): DetailAction()
}