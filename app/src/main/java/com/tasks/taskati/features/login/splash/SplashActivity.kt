package com.tasks.taskati.features.login.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tasks.taskati.R
import com.tasks.taskati.common.Navigation
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity(R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalScope.launch {
            delay(3000)
            Navigation.goToLoginActivity(this@SplashActivity)
        }
    }
}
