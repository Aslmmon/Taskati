package com.example.taskati.common.di

import com.example.taskati.common.Repo.HomeRepo
import com.example.taskati.common.Repo.IHome
import org.koin.dsl.module

val repositoriesModule = module {
    single { HomeRepo(get()) as IHome }
//    single { LoginRepo(get()) }
//    single { MainRepo(get()) as IMain }

}