package com.isain.brastlewark.ui.home

import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.GridLayoutManager
import com.isain.brastlewark.R
import com.isain.brastlewark.services.responses.Inhabitant
import com.isainsosa.arco.renderer.ViewModelRenderer
import kotlinx.android.synthetic.main.view_home.view.*

class HomeRenderer(view: View, homeViewModel: HomeViewModel): ViewModelRenderer<HomeAction, HomeState, HomeViewModel>(view, homeViewModel), PopupMenu.OnMenuItemClickListener {

    var adapter: HomeAdapter

    init {
        view.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(searchQuery: String?): Boolean {
                controller.dispatch(HomeAction.SearchInhabitant(searchQuery ?: ""))
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
        })

        adapter = HomeAdapter(object : HomeAdapterListener {
            override fun onItemClicked(inhabitant: Inhabitant) {
                controller.dispatch(HomeAction.InhabitantClicked(inhabitant))
            }
        })
        view.inhabitants.apply {
            layoutManager = GridLayoutManager(view.context, 3)
            adapter = this@HomeRenderer.adapter

            if (viewModel.inhabitants.isNotEmpty()) {
                this@HomeRenderer.adapter.loadInhabitants(viewModel.inhabitants)
            }
        }

        view.filter.setOnClickListener { showFiltersPopup() }
    }

    override fun render(state: HomeState) {
        when(state) {
            is HomeState.RetrievingCensusData -> {
                view.progressBar.visibility = View.VISIBLE
            }
            is HomeState.RetrieveCensusDataSuccess -> {
                view.progressBar.visibility = View.GONE
                adapter.loadInhabitants(viewModel.inhabitants)
            }
            is HomeState.RetrieveCensusDataError -> {
                view.progressBar.visibility = View.GONE
            }
            is HomeState.SearchingInhabitant -> {
                adapter.searchInhabitant(viewModel.searchQuery)
            }
        }
    }

    private fun showFiltersPopup () {
        val popupMenu = PopupMenu(view.context, view.filter)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.filters_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(this)
        popupMenu.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.age -> adapter.sortBy(HomeAdapter.SortType.AGE)
            R.id.weight -> adapter.sortBy(HomeAdapter.SortType.WEIGHT)
            R.id.height -> adapter.sortBy(HomeAdapter.SortType.HEIGHT)
            R.id.none -> adapter.sortBy(HomeAdapter.SortType.NONE)
        }
        return true
    }
}