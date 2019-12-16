package com.isain.brastlewark.ui.detail

import android.view.View
import com.isain.brastlewark.R
import com.isain.brastlewark.managers.ImageLoaderManager
import com.isainsosa.arco.renderer.ViewModelRenderer
import kotlinx.android.synthetic.main.view_detail.view.*

class DetailRenderer(view: View, detailViewModel: DetailViewModel): ViewModelRenderer<DetailAction, DetailState, DetailViewModel>(view, detailViewModel) {

    init {
        view.backBtn.setOnClickListener {
            controller.dispatch(DetailAction.GoBack)
        }
    }

    override fun render(state: DetailState) {
        when(state) {
            is DetailState.ShowDetail -> {
                viewModel.inhabitant?.let { inhabitant ->
                    view.title.text = inhabitant.name
                    ImageLoaderManager.loadImage(inhabitant.thumbnail, view.profileImage)
                    val resources = view.context.resources
                    view.ageTv.text = resources.getString(R.string.inhabitant_age, inhabitant.age.toString())
                    view.weightTv.text = resources.getString(R.string.inhabitant_weight, inhabitant.weight.toString())
                    view.heightTv.text = resources.getString(R.string.inhabitant_height, inhabitant.height.toString())
                }
            }
        }
    }
}