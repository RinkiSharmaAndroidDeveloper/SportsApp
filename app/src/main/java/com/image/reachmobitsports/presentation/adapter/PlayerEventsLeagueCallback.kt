package com.image.reachmobitsports.presentation.adapter

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.image.reachmobitsports.data.i.responses.Player

class PlayerEventsLeagueCallback(
    private val oldList: MutableList<Player>,
    private val newList: MutableList<Player>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList == newList
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val  nameOld = oldList[oldItemPosition].playerName
        val nameNew = newList[newItemPosition].playerName
        return nameOld == nameNew
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}