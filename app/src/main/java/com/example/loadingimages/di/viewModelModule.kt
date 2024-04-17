package com.example.loadingimages.di

import com.example.loadingimages.features.photoListScreen.PhotoListScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::PhotoListScreenViewModel)
}