package com.image.reachmobitsports.data.remote.api

import com.image.reachmobitsports.data.remote.response.*
import com.image.reachmobitsports.data.remote.responses.PlayerListResponse
import com.image.reachmobitsports.presentation.util.ConstSports
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET(ConstSports.GET_ALL_TEAMS_IN_LEAGUE)
    suspend fun getAllTeamsInALeague(@Query("l") league: String): Response<TeamsResponse>

    @GET(ConstSports.GET_ALL_TEAM_PLAYERS)
    suspend fun getTeamPlayer(@Query("t") league: String): Response<PlayerListResponse>

    @GET(ConstSports.GET_TEAM_PLAYER)
    suspend fun searchPlayer(@Query("p") playerName: String): Response<PlayerListResponse>
}