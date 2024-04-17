package com.example.loadingimages.di

import com.example.loadingimages.data.repositories.PhotoRepositoryImpl
import com.example.loadingimages.domain.repositories.PhotoRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoriesModule = module {
    singleOf(::PhotoRepositoryImpl) { bind<PhotoRepository>() }
}