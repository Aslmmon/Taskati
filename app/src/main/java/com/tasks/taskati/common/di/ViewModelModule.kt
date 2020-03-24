package com.tasks.taskati.common.di


import com.tasks.taskati.features.details.DetailsViewModel
import com.tasks.taskati.features.home.HomeViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { HomeViewModel(get()) }
    single { DetailsViewModel(get(),get()) }

}