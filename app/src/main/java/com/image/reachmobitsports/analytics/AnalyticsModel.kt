package com.image.reachmobitsports.analytics

import androidx.lifecycle.ViewModel

abstract class AnalyticsModel<T:ViewModel> {
    protected lateinit var viewModel: T

    fun getScreenViewModel():T =viewModel

    fun setSecreenViewModel(model: T){
        viewModel =model
    }
    protected open fun onScreenViewModelAttached(){
        //
    }
}