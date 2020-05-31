package com.example.dungeonsanddata.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.player_item_layout.view.*
import com.example.dungeonsanddata.R
import com.example.dungeonsanddata.data.model.Player

class MainAdapter(private val players: ArrayList<Player>) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindPlayer(player: Player) {
            itemView.textViewPlayerName.text = player.name
            Glide.with(itemView.imageViewAvatar.context).load(player.avatar).into(itemView.imageViewAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.player_item_layout, parent, false
        )
    )

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bindPlayer(players[position])

    fun addData(list: List<Player>) {
        players.addAll(list)
    }

}