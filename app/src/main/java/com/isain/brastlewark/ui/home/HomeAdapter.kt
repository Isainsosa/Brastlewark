package com.isain.brastlewark.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.isain.brastlewark.R
import com.isain.brastlewark.services.responses.Inhabitant
import kotlinx.android.synthetic.main.card_inhabitant_profile.view.*

interface HomeAdapterListener {
    fun onItemClicked(inhabitant: Inhabitant)
}

class HomeAdapter(val listener: HomeAdapterListener): RecyclerView.Adapter<HomeAdapter.InhabitantsViewHolder>() {

    val inhabitants: MutableList<Inhabitant> = mutableListOf()
    val inhabitantsFiltered: MutableList<Inhabitant> = mutableListOf()
    var searchQuery: String = ""
    var sortType = SortType.NONE

    fun loadInhabitants(inhabitants: List<Inhabitant>) {
        this.inhabitants.clear()
        this.inhabitants.addAll(inhabitants)
        notifyDataSetChanged()
    }

    fun searchInhabitant(searchQuery: String) {
        this.searchQuery = searchQuery
        inhabitantsFiltered.clear()

        if (searchQuery.isNotEmpty()) {
            inhabitants.map { inhabitant ->
                if (inhabitant.name.contains(searchQuery) || inhabitant.professions.filter { it.contains(searchQuery) }.isNotEmpty()) {
                    inhabitantsFiltered.add(inhabitant)
                }
            }
        }
        notifyDataSetChanged()
    }

    fun sortBy(sortType: SortType) {
        this.sortType = sortType
        inhabitantsFiltered.clear()
        inhabitantsFiltered.addAll(inhabitants)
        when(sortType) {
            SortType.AGE -> {
                inhabitantsFiltered.sortBy {
                    it.age
                }
            }
            SortType.WEIGHT -> {
                inhabitantsFiltered.sortBy {
                    it.weight
                }
            }
            SortType.HEIGHT -> {
                inhabitantsFiltered.sortBy {
                    it.height
                }
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InhabitantsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return InhabitantsViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return if(searchQuery.isEmpty()) inhabitants.size else inhabitantsFiltered.size
    }

    override fun onBindViewHolder(holder: InhabitantsViewHolder, position: Int) {
        val inhabitant: Inhabitant = if((searchQuery.isNotEmpty() || sortType != SortType.NONE) && inhabitantsFiltered.isNotEmpty()) inhabitantsFiltered[position] else inhabitants[position]
        holder.bind(inhabitant, position)
    }

    inner class InhabitantsViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder(inflater.inflate(R.layout.card_inhabitant_profile, parent, false)) {
        fun bind(inhabitant: Inhabitant, position: Int) {
            itemView.container.setOnClickListener{
                listener.onItemClicked(inhabitant)
            }
            itemView.name.text = inhabitant.name
            Glide.with(itemView.context)
                .load(inhabitant.thumbnail)
                .into(itemView.thumbnail)
        }
    }

    enum class SortType {
        AGE,
        WEIGHT,
        HEIGHT,
        NONE
    }
}