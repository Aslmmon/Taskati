package com.example.taskati.common.di


import com.example.taskati.common.network.createNetworkClient
import org.koin.dsl.module
import retrofit2.Retrofit


val retrofit: Retrofit = createNetworkClient()
//private val api: Api = retrofit.create(Api::class.java)
//private val mainApi: MainApi = retrofit.create(MainApi::class.java)
//private val loginApi: LoginApi = retrofit.create(LoginApi::class.java)

val networkModule = module {
//    single { api }
//    single { loginApi }
//    single { mainApi }
}