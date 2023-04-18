package com.image.reachmobitsports.presentation.state

import com.image.reachmobitsports.di.response.Teams


data class SportsLeagueState(
    val leagues: List<Teams>? = arrayListOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)