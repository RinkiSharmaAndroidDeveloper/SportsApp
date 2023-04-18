package com.image.reachmobitsports.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.image.reachmobitsports.di.remote.Repository
import com.image.reachmobitsports.presentation.state.PlayersState
import com.image.reachmobitsports.presentation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchQueryViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var _searchPlayersMutable: MutableLiveData<PlayersState> = MutableLiveData()
    val searchPlayersLive: MutableLiveData<PlayersState> get() = _searchPlayersMutable

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