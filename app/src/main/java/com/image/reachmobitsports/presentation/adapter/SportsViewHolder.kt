package com.image.reachmobitsports.presentation.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.image.reachmobitsports.R

class SportsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var albumImage: ImageView
    var teamName: TextView
    var dateText: TextView

    init {
        albumImage = view.findViewById(R.id.img_sport_home)
        teamName = view.findViewById(R.id.tv_sport_home)
        dateText = view.findViewById(R.id.tv_sport_since_home)
    }
}