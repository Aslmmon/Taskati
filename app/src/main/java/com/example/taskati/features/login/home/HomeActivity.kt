package com.example.taskati.features.login.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.Observer
import com.example.taskati.R
import com.example.taskati.common.Navigation
import com.example.taskati.common.bases.setSafeOnClickListener
import com.example.taskati.common.data.db.TaskTable
import com.example.taskati.features.login.home.adapter.TaskAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity(R.layout.activity_home), TaskAdapter.Interaction {

    private val homeViewModel: HomeViewModel by viewModel()
    lateinit var taskAdapter:TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecycler()

        homeViewModel.tasksResponse.observe(this, Observer {
            Log.i(javaClass.simpleName, it.toString())
            taskAdapter.submitList(it)
        })

        homeViewModel.taskSaved.observe(this, Observer {
            if(it) homeViewModel.getTaks()
        })


        fab_btn_add.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.taks_bottom_sheet, null)
            val create_btn = view.findViewById<MaterialButton>(R.id.btn_create_task)
            val taskTitle = view.findViewById<EditText>(R.id.et_create_task)


            create_btn.setSafeOnClickListener {
                dialog.dismiss()
                val title= taskTitle.text.toString()
                val task = TaskTable(date = "1", difficulty = 1, isDone = false, title = title,id = 0)
                homeViewModel.saveTask(task)
                //onResume()
            }
            dialog.setContentView(view)
            dialog.show()
        }
    }

    private fun initRecycler() {
        rv_tasks.apply {
            taskAdapter = TaskAdapter(this@HomeActivity)
            adapter = taskAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getTaks()

    }

    override fun onItemSelected(position: Int, item: TaskTable) {
        Log.i(javaClass.simpleName,item.title)
        Navigation.goToDetailsActivity(this,item)
    }
}
