package com.image.reachmobitsports.di.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class TeamsResponse(
    @SerializedName("teams")
    val teams: List<Teams>
)

@Parcelize
data class Teams(
    @SerializedName("idTeam")
    val teamId: String,
    @SerializedName("strTeam")
    val teamName: String,
    @SerializedName("strTeamBadge")
    val badgeUrl: String,
    @SerializedName("intFormedYear")
    val formedYear: String,
    @SerializedName("idLeague")
    val idLeague: String
) : Parcelable
