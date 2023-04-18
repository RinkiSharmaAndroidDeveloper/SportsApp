package com.image.reachmobitsports.analytics

import javax.inject.Provider

class AnalyticsModelProvider(private val analyticsModelMap: Map<Class<out AnalyticsModel<*>>, Provider<AnalyticsModel<*>>>) {

    @Suppress("UNCHECKED_CAST")
    operator fun <T : AnalyticsModel<*>> get(modelClass: Class<T>): T? {
        val analyticsModel = analyticsModelMap[modelClass]?.get() ?: return null

        return analyticsModel as T
    }
}