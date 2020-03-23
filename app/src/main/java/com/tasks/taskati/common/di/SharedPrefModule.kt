package com.floriaapp.vendor.common.di

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

//val sharedPref = module {
//
//    single{
//        getSharedPrefrences(androidApplication())
//    }
//
//
//}
//
//fun getSharedPrefrences(androidApplication: Application): SharedPrefrencesWrapper {
//    return SharedPrefrencesWrapper(App.context.getSharedPreferences("Floria_Vendor",Context.MODE_PRIVATE))
//}