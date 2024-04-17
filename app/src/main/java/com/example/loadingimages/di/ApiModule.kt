package com.example.loadingimages.di

import com.example.loadingimages.data.remote.ApiService
import org.koin.dsl.module

val apiModule = module {
    single { ApiService(get()) }
}