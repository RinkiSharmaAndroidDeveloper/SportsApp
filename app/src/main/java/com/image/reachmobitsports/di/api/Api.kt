package com.image.reachmobitsports.di.api

import com.image.reachmobitsports.data.i.responses.PlayerListResponse
import com.image.reachmobitsports.di.response.TeamsResponse
import com.image.reachmobitsports.presentation.util.Const
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET(Const.GET_ALL_TEAMS_IN_LEAGUE)
    suspend fun getAllTeamsInALeague(@Query("l") league: String): Response<TeamsResponse>

    @GET(Const.GET_ALL_TEAM_PLAYERS)
    suspend fun getTeamPlayer(@Query("t") league: String): Response<PlayerListResponse>

    @GET(Const.GET_TEAM_PLAYER)
    suspend fun searchPlayer(@Query("p") playerName: String): Response<PlayerListResponse>
}