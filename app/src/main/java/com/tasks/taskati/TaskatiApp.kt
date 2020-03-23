package com.tasks.taskati

import android.app.Application
import android.content.Context
import com.tasks.taskati.common.di.databaseModule
import com.tasks.taskati.common.di.networkModule
import com.tasks.taskati.common.di.repositoriesModule
import com.tasks.taskati.common.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TaskatiApp : Application() {

    companion object {
        lateinit var context: Context
        fun getAppContext(): Context {
            return context
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        startKoin {
            androidContext(this@TaskatiApp)
            androidLogger()
            modules(listOf(viewModelModule, networkModule, repositoriesModule,databaseModule))
        }
    }
}
