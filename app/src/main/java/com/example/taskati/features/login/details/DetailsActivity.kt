package com.example.taskati.features.login.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.taskati.R
import com.example.taskati.common.Constants
import com.example.taskati.common.data.db.TaskTable
import com.example.taskati.features.login.home.HomeViewModel
import kotlinx.android.synthetic.main.activity_details.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailsActivity : AppCompatActivity(R.layout.activity_details) {

    private val detailViewModel: DetailsViewModel by viewModel()
    lateinit var task:TaskTable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent.extras
        intent?.let {
            val dataRecieved = it.getSerializable(Constants.TASK_DETAILS) as TaskTable
            task = dataRecieved
            Log.i(javaClass.simpleName, dataRecieved.toString())
            bindDataToViews(dataRecieved)
        }


    }

    private fun bindDataToViews(dataRecieved: TaskTable) {
        tv_date.text = dataRecieved.date

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.itemId
            if (id == R.id.action_delete) {
            Log.i(javaClass.simpleName, task.toString())
            detailViewModel.deleteTask(task)
            Toast.makeText(this,"eshta",Toast.LENGTH_SHORT).show()
        }
            return false
    }
}
