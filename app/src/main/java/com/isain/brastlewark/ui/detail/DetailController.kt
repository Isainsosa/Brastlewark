package com.isain.brastlewark.ui.detail

import android.os.Bundle
import android.view.View
import com.isain.brastlewark.MainActivity
import com.isain.brastlewark.R
import com.isainsosa.arco.controller.BaseFragmentController
import com.isainsosa.arco.extensions.handleBack
import com.isainsosa.arco.extensions.removeFragment
import com.isainsosa.arco.renderer.ViewModelRenderer

class DetailController(bundle: Bundle): BaseFragmentController<DetailAction, DetailState, DetailViewModel>(bundle) {

    companion object {
        private const val inhabitantIdKey = "inhabitantIdKey"

        fun instance(inhabitantId: Long): DetailController {
            return DetailController(Bundle().apply {
                putLong(inhabitantIdKey, inhabitantId)
            })
        }
    }

    override val layoutId = R.layout.view_detail

    override val viewModelClass = DetailViewModel::class.java

    override fun getArcoRenderer(view: View, viewModel: DetailViewModel) = DetailRenderer(view, viewModel)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arcoController.dispatch(DetailAction.GetDetail(arguments?.getLong(inhabitantIdKey) ?: 0))
    }

    override fun onAction(action: DetailAction) {
        super.onAction(action)
        when(action) {
            is DetailAction.GoBack -> goBack()
        }
    }

    private fun goBack() {
        val mainActivity = activity as MainActivity
        mainActivity.supportFragmentManager.handleBack()
    }
}