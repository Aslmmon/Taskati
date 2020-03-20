package com.example.taskati.common.di


import com.example.taskati.features.login.details.DetailsViewModel
import com.example.taskati.features.login.home.HomeViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { HomeViewModel(get()) }
    single { DetailsViewModel(get(),get()) }

}