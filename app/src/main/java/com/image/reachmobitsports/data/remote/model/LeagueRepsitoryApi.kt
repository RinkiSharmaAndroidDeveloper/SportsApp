package com.image.reachmobitsports.data.remote.model

import com.image.reachmobitsports.presentation.util.Resource
import com.image.reachmobitsports.data.remote.response.TeamsResponse
import com.image.reachmobitsports.data.remote.responses.PlayerListResponse

interface LeagueRepsitoryApi {
    suspend fun getAllLeagues(): Resource<TeamsResponse>
    suspend fun getTeamPlayer(): Resource<PlayerListResponse>
    suspend fun searchTeamPlayer(name: String): Resource<PlayerListResponse>
}