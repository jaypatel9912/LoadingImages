package com.example.loadingimages

import android.app.Application
import com.example.loadingimages.di.apiModule
import com.example.loadingimages.di.networkModule
import com.example.loadingimages.di.repositoriesModule
import com.example.loadingimages.di.useCasesModule
import com.example.loadingimages.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                networkModule,
                apiModule,
                repositoriesModule,
                useCasesModule,
                viewModelModule
            )
        }
    }
}