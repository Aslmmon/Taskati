package com.example.taskati.features.login.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.taskati.R
import com.example.taskati.common.Constants
import com.example.taskati.common.data.db.TaskTable
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity(R.layout.activity_details) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent.extras
        intent?.let {
            val dataRecieved = it.getSerializable(Constants.TASK_DETAILS) as TaskTable
            Log.i(javaClass.simpleName, dataRecieved.toString())
            bindDataToViews(dataRecieved)
        }
    }

    private fun bindDataToViews(dataRecieved: TaskTable) {
        tv_date.text = dataRecieved.date
    }
}
