package com.tasks.taskati.common.di


import com.tasks.taskati.features.login.details.DetailsViewModel
import com.tasks.taskati.features.login.home.HomeViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { HomeViewModel(get()) }
    single { DetailsViewModel(get(),get()) }

}