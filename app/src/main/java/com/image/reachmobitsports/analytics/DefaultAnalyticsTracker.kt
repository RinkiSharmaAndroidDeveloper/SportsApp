package com.image.reachmobitsports.analytics

import android.util.Log
import javax.inject.Inject

class DefaultAnalyticsTracker @Inject constructor():AnalyticsTracker {
    override fun logEvent(
        event: String,
        vararg param: String
    ) {
        Log.i("AnalyticsLog", "Event: $event, Params: ${param.toList()}")
    }
}