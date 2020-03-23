package com.tasks.taskati.common.di

import com.tasks.taskati.common.Repo.DetailRepo.DetailRepo
import com.tasks.taskati.common.Repo.DetailRepo.IDetail
import com.tasks.taskati.common.Repo.HomeRepo.HomeRepo
import com.tasks.taskati.common.Repo.HomeRepo.IHome
import org.koin.dsl.module

val repositoriesModule = module {
    single { HomeRepo(get(),get()) as IHome }
    single { DetailRepo(get(),get()) as IDetail }
//    single { MainRepo(get()) as IMain }

}