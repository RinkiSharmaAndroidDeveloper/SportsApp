package com.image.reachmobitsports.di.remote

import com.image.reachmobitsports.analytics.AnalyticsModel
import com.image.reachmobitsports.analytics.AnalyticsModelProvider
import com.image.reachmobitsports.analytics.AnalyticsTracker
import com.image.reachmobitsports.analytics.DefaultAnalyticsTracker
import com.image.reachmobitsports.presentation.viewmodel.AnalyticsEventViewModels
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyticsModule {
    @Binds
    abstract fun bindAnalyticsTracker(tracker: DefaultAnalyticsTracker): AnalyticsTracker

    @Binds
    @IntoMap
    @AnalyticsModelKey(AnalyticsEventViewModels::class)
    internal abstract fun provideDashboardScreenAnalyticsModel(
        analyticsModel: AnalyticsEventViewModels
    ): AnalyticsModel<*>

    companion object {
        @Provides
        @Singleton
        internal fun provideAnalyticsModelProvider(
            analyticsModelMap: Map<Class<out AnalyticsModel<*>>, @JvmSuppressWildcards Provider<AnalyticsModel<*>>>
        ): AnalyticsModelProvider = AnalyticsModelProvider(analyticsModelMap)
    }
}