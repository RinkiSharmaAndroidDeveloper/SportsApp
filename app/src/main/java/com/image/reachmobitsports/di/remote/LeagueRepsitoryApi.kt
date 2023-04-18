package com.image.reachmobitsports.di.remote

import com.image.reachmobitsports.presentation.util.Resource
import com.image.reachmobitsports.di.response.TeamsResponse
import com.image.reachmobitsports.data.i.responses.PlayerListResponse

interface LeagueRepsitoryApi {
    suspend fun getAllLeagues(): Resource<TeamsResponse>
    suspend fun getTeamPlayer(): Resource<PlayerListResponse>
    suspend fun searchTeamPlayer(name: String): Resource<PlayerListResponse>
}