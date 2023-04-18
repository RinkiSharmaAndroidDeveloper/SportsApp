package com.image.reachmobitsports.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics.Event
import com.image.reachmobitsports.analytics.AnalyticsModel
import com.image.reachmobitsports.analytics.AnalyticsTracker
import com.image.reachmobitsports.presentation.util.Const
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class AnalyticsEventViewModels @Inject constructor(private val analyticsTracker: AnalyticsTracker) :
    AnalyticsModel<SearchQueryViewModel>() {

    override fun onScreenViewModelAttached() {
        viewModel.viewModelScope.launch {
            withContext(Dispatchers.Default) {
              analyticsTracker.logEvent(Event.SCREEN_VIEW, Const.PLAYER_FRAGMENT)
            }
        }
    }
}