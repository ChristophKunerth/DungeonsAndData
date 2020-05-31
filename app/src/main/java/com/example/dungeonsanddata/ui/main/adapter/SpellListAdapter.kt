package com.example.dungeonsanddata.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dungeonsanddata.R
import com.example.dungeonsanddata.data.model.Spell
import kotlinx.android.synthetic.main.spell_item_layout.view.*
import java.util.*

class SpellListAdapter(private val spells: ArrayList<Spell>) : RecyclerView.Adapter<SpellListAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindSpell(
            spell: Spell
        ) {
            itemView.textViewSpellName.text = spell.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.spell_item_layout, parent, false
        )
    )

    override fun getItemCount(): Int = spells.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bindSpell(spells[position])
    }

    fun addData(list: List<Spell>) {
        spells.addAll(list)
    }

}