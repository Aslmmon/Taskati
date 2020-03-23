package com.tasks.taskati.common.di

import androidx.room.Room
import com.tasks.taskati.common.data.db.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "tasks_database")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<AppDatabase>().taskDao() }
    single { get<AppDatabase>().commentDao() }

}