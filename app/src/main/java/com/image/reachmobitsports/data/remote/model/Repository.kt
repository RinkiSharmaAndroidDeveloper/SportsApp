package com.image.reachmobitsports.data.remote.model

import com.image.reachmobitsports.presentation.util.Resource
import com.image.reachmobitsports.data.remote.api.Api
import com.image.reachmobitsports.data.remote.response.*
import com.image.reachmobitsports.data.remote.responses.PlayerListResponse
import com.image.reachmobitsports.presentation.util.Const
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(private val api: Api) : LeagueRepsitoryApi {

    override suspend fun getAllLeagues(): Resource<TeamsResponse> {
        return try {
            withContext(Dispatchers.IO) {
                Resource.Loading(
                    loadingStatus = true,
                    data = null
                )
                Resource.Success(
                    data = api.getAllTeamsInALeague(Const.LEAGUE_THESPORTDB).body()
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.IO) {
                Resource.Failure(
                    e.localizedMessage ?: "An unknown error occurred"
                )
            }
        }
    }

    override suspend fun getTeamPlayer(): Resource<PlayerListResponse> {
        return try {
            withContext(Dispatchers.IO) {
                Resource.Loading(
                    loadingStatus = true,
                    data = null
                )

                Resource.Success(
                    data = api.getTeamPlayer(Const.EXTRA_TEAM_PLAYER).body()
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.IO) {
                Resource.Failure(
                    e.localizedMessage ?: "An unknown error occurred"
                )
            }
        }
    }

    override suspend fun searchTeamPlayer(name: String): Resource<PlayerListResponse> {
        return try {
            withContext(Dispatchers.IO) {
                Resource.Loading(
                    loadingStatus = true,
                    data = null
                )

                Resource.Success(
                    data = api.searchPlayer(name).body()
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.IO) {
                Resource.Failure(
                    e.localizedMessage ?: "An unknown error occurred"
                )
            }
        }
    }

}