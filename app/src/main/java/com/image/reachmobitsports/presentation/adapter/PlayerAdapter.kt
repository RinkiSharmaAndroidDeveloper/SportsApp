package com.image.reachmobitsports.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.image.reachmobitsports.R
import com.image.reachmobitsports.data.remote.responses.Player
import com.image.reachmobitsports.databinding.ItemEventLeagueHomeBinding

class PlayerAdapter(private var listEventsLeagueData: MutableList<Player>) :
    RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {


    inner class ViewHolder(private val binding: ItemEventLeagueHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(player: Player) {
            binding.apply {
                playerName.text = player.playerName

                Glide.with(playerImg.context)
                    .load(player.playerImage)
                    .encodeQuality(10)
                    .placeholder(R.drawable.ic_default_foreground)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .centerCrop()
                    .into(playerImg)
            }

        }
    }

    fun setEventsLeagueData(player: MutableList<Player>) {
        if (player.isNotEmpty()) {
            val diffCallback = PlayerEventsLeagueCallback(listEventsLeagueData, player)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            this.listEventsLeagueData.clear()
            this.listEventsLeagueData.addAll(player)
            diffResult.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemEventLeagueHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listEventsLeagueData[position])
    }

    override fun getItemCount(): Int {
        return listEventsLeagueData.size
    }

}