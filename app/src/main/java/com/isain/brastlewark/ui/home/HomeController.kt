package com.isain.brastlewark.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.isain.brastlewark.MainActivity
import com.isain.brastlewark.R
import com.isain.brastlewark.ui.detail.DetailController
import com.isainsosa.arco.controller.BaseFragmentController
import com.isainsosa.arco.extensions.FragmentAnimation
import com.isainsosa.arco.extensions.replaceFragment
import kotlinx.android.synthetic.main.activity_main.*

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
        when(action) {
            is HomeAction.InhabitantClicked -> {
                showDetail(DetailController.instance(action.inhabitant.id))
            }
        }
    }

    private fun showDetail(detailController: Fragment) {
        val mainActivity = activity as MainActivity
        mainActivity.container.let { mainActivity.supportFragmentManager.replaceFragment(it.id, detailController, true, FragmentAnimation.HORIZONTAL_ANIM) }
    }
}