package com.example.taskati.common.di

import com.example.taskati.common.Repo.DetailRepo.DetailRepo
import com.example.taskati.common.Repo.DetailRepo.IDetail
import com.example.taskati.common.Repo.HomeRepo.HomeRepo
import com.example.taskati.common.Repo.HomeRepo.IHome
import org.koin.dsl.module

val repositoriesModule = module {
    single { HomeRepo(get()) as IHome }
    single { DetailRepo(get(),get()) as IDetail }
//    single { MainRepo(get()) as IMain }

}