package com.image.reachmobitsports.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.image.reachmobitsports.presentation.util.Resource
import com.image.reachmobitsports.data.remote.model.Repository
import com.image.reachmobitsports.presentation.state.PlayersState
import com.image.reachmobitsports.presentation.state.SportsLeagueState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var mutableLiveData: MutableLiveData<SportsLeagueState> = MutableLiveData()
    val liveData: MutableLiveData<SportsLeagueState> get() = mutableLiveData

    private var _playersStateMutable: MutableLiveData<PlayersState> = MutableLiveData()
    val playersStateLive: MutableLiveData<PlayersState> get() = _playersStateMutable

    private var _searchPlayersMutable: MutableLiveData<PlayersState> = MutableLiveData()
    val searchPlayersLive: MutableLiveData<PlayersState> get() = _searchPlayersMutable

   //This method fetches all the teams in a league and returns a
    fun getSportsList() {
        viewModelScope.launch {
            when (val result = repository.getAllLeagues()) {
                is Resource.Loading -> {
                    mutableLiveData.value = SportsLeagueState(
                        leagues = result.data?.teams,
                        isLoading = result.loadingStatus,
                        error = result.message
                    )
                }
                is Resource.Success -> {
                    mutableLiveData.value = SportsLeagueState(
                        leagues = result.data?.teams,
                        isLoading = result.loadingStatus,
                        error = result.message
                    )
                }
                is Resource.Failure -> {
                    mutableLiveData.value = SportsLeagueState(
                        leagues = result.data?.teams,
                        isLoading = result.loadingStatus,
                        error = result.message
                    )
                    Log.e("Error", result.message ?: "Unknown error")
                }
            }
        }
    }

    fun getPlayersList() {
        viewModelScope.launch {
            when (val result = repository.getTeamPlayer()) {
                is Resource.Loading -> {
                    _playersStateMutable.value = PlayersState(
                        player = result.data?.player,
                        isLoading = result.loadingStatus,
                        error = result.message
                    )
                }
                is Resource.Success -> {
                    _playersStateMutable.value = PlayersState(
                        player = result.data?.player,
                        isLoading = result.loadingStatus,
                        error = result.message
                    )
                }
                is Resource.Failure -> {
                    _playersStateMutable.value = PlayersState(
                        player = result.data?.player,
                        isLoading = result.loadingStatus,
                        error = result.message
                    )
                    Log.e("Error", result.message ?: "Unknown error")
                }
            }
        }
    }

    fun getSerachPlayer(playerName: String) {
        viewModelScope.launch {
            when (val result = repository.searchTeamPlayer(playerName)) {
                is Resource.Loading -> {
                    _searchPlayersMutable.value = PlayersState(
                        player = result.data?.player,
                        isLoading = result.loadingStatus,
                        error = result.message
                    )
                }
                is Resource.Success -> {
                    _searchPlayersMutable.value = PlayersState(
                        player = result.data?.player,
                        isLoading = result.loadingStatus,
                        error = result.message
                    )
                }
                is Resource.Failure -> {
                    _searchPlayersMutable.value = PlayersState(
                        player = result.data?.player,
                        isLoading = result.loadingStatus,
                        error = result.message
                    )
                    Log.e("Error", result.message ?: "Unknown error")
                }
            }
        }
    }

}
