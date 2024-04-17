package com.example.loadingimages.di

import com.example.loadingimages.domain.usecases.photos.PhotosUseCases
import com.example.loadingimages.domain.usecases.photos.PhotosUseCasesImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCasesModule = module {
    singleOf(::PhotosUseCasesImpl) { bind<PhotosUseCases>() }
}