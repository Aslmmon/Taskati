package com.example.taskati.features.login.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.taskati.R
import com.example.taskati.common.Constants

class DetailsActivity : AppCompatActivity(R.layout.activity_details) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent.extras
        intent?.let {
            val dataRecieved = it.getSerializable(Constants.TASK_DETAILS)
            Log.i(javaClass.simpleName,dataRecieved.toString())
        }
    }
}
