package com.image.reachmobitsports.analytics

interface AnalyticsTracker {
    fun logEvent(event: String, vararg param: String)
}