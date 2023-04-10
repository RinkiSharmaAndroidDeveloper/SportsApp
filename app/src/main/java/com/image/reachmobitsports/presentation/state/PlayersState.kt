package com.image.reachmobitsports.presentation.state

import com.image.reachmobitsports.data.remote.responses.Player

data class PlayersState(
    val player: MutableList<Player>? = arrayListOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)