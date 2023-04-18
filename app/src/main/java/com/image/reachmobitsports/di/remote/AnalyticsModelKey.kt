package com.image.reachmobitsports.di.remote

import com.image.reachmobitsports.analytics.AnalyticsModel
import dagger.MapKey
import kotlin.reflect.KClass
@MapKey
annotation class AnalyticsModelKey(val value: KClass<out AnalyticsModel<*>>)