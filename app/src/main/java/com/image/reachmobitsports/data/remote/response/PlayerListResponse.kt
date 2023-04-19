package com.image.reachmobitsports.data.remote.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class PlayerListResponse(
    @SerializedName("player")
    val player: MutableList<Player>
)

@Parcelize
data class Player(
    @SerializedName("strTeam")
    val strTeam: String,
    @SerializedName("strNationality")
    val playCountry: String,
    @SerializedName("strPlayer")
    val playerName: String,
    @SerializedName("strSport")
    val sportName: String,
    @SerializedName("strThumb")
    val playerImage: String,
): Parcelable