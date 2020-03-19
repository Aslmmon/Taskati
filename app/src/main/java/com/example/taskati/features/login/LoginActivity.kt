package com.example.taskati.features.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taskati.R
import com.example.taskati.common.Navigation
import com.example.taskati.common.bases.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(R.layout.activity_login) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btn_login.setSafeOnClickListener {
            Navigation.goToHomeActivity(this)
        }
    }
}
