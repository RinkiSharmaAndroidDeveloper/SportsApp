package com.image.reachmobitsports.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.image.reachmobitsports.R
import com.image.reachmobitsports.data.remote.response.Teams


class SportsListAdapter (
    private val galleryItemList: MutableList<Teams>

) :
    RecyclerView.Adapter<SportsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sports_list_item, parent, false)
        return SportsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SportsViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(galleryItemList[position].badgeUrl)
            .placeholder(R.drawable.no_image)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .fitCenter()
            .dontAnimate()
            .into(holder.albumImage)
        holder.dateText.text = galleryItemList[position].formedYear
        holder.teamName.text = galleryItemList[position].teamName
    }

    override fun getItemCount() = galleryItemList.size
}