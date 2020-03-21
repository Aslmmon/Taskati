package com.example.taskati.features.login.home

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.CompoundButton
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.taskati.R
import com.example.taskati.common.Navigation
import com.example.taskati.common.bases.setSafeOnClickListener
import com.example.taskati.common.data.db.TaskTable
import com.example.taskati.features.login.home.adapter.TaskAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.toast
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class HomeActivity : AppCompatActivity(R.layout.activity_home), TaskAdapter.Interaction {

    private val homeViewModel: HomeViewModel by viewModel()
    lateinit var taskAdapter: TaskAdapter
    lateinit var doneList: List<TaskTable>
    lateinit var allTasks: MutableList<TaskTable>
    val df = SimpleDateFormat("dd-MMM-yyyy")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecycler()
        homeViewModel.tasksResponse.observe(this, Observer {
            filterDonelist(it)
            taskAdapter.submitList(it)
        })
        homeViewModel.taskSaved.observe(this, Observer {
            if (it) getTasks()
        })
        fab_btn_add.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.taks_bottom_sheet, null)
            val create_btn = view.findViewById<MaterialButton>(R.id.btn_create_task)
            val taskTitle = view.findViewById<EditText>(R.id.et_create_task)
            create_btn.setSafeOnClickListener {
                dialog.dismiss()
                val date = Calendar.getInstance().time
                val formattedDate: String = df.format(date)
                val title = taskTitle.text.toString()
                val task = TaskTable(
                    date = formattedDate,
                    difficulty = 0,
                    isDone = false,
                    title = title,
                    id = 0
                )
                homeViewModel.saveTask(task)
            }
            dialog.setContentView(view)
            dialog.show()
        }
    }

    private fun filterDonelist(it: List<TaskTable>) {
        doneList = it.filter { it.isDone }
    }

    private fun initRecycler() {
        rv_tasks.apply {
            taskAdapter = TaskAdapter(this@HomeActivity)
            adapter = taskAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        getTasks()
    }

    private fun getTasks() {
        homeViewModel.getTasks()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.action_all_tasks -> getTasks()
            R.id.action_done_tasks -> { taskAdapter.submitList(doneList) }
        }
        return true
    }

    override fun onItemSelected(position: Int, item: TaskTable) {
        Log.i(javaClass.simpleName,item.toString())
        Navigation.goToDetailsActivity(this, item)
    }

    override fun onCheckSelected(btn: CompoundButton, isDone: Boolean, item: TaskTable) {
        val newItem = TaskTable(item.id,item.title,item.date,isDone,item.difficulty)
        homeViewModel.updateDoneTask(newItem)
        onResume()
    }

    override fun onIndicatorChecked(position: Int, item: TaskTable) {
        homeViewModel.updatePeriorityTask(item.id, position)
        onResume()
    }


}

