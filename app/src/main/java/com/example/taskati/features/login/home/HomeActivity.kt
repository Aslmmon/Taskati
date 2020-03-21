package com.example.taskati.features.login.home

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.taskati.R
import com.example.taskati.common.Constants
import com.example.taskati.common.Navigation
import com.example.taskati.common.bases.setSafeOnClickListener
import com.example.taskati.common.bases.showAlertDialog
import com.example.taskati.common.data.db.TaskTable
import com.example.taskati.common.model.UserWithComments
import com.example.taskati.features.login.home.adapter.TaskAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.empty_view.*
import kotlinx.android.synthetic.main.loading.*
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class HomeActivity : AppCompatActivity(R.layout.activity_home), TaskAdapter.Interaction {

    private val homeViewModel: HomeViewModel by viewModel()
    lateinit var taskAdapter: TaskAdapter
    lateinit var doneList: List<TaskTable>
    private lateinit var database: DatabaseReference

    // ...
    val df = SimpleDateFormat("dd-MMM-yyyy")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecycler()
        initFirebase()

        homeViewModel.tasksResponse.observe(this, Observer {
            stopLoading()
            if (it.isEmpty()) {
                taskAdapter.submitList(it)
                showEmptyView()
            } else {
                hideEmptyView()
                filterDonelist(it)
                taskAdapter.submitList(it)
            }

        })
        homeViewModel.taskSaved.observe(this, Observer {
            if (it) getTasks()
        })


        homeViewModel.allTasksWithComments.observe(this, Observer {
            Log.i(javaClass.simpleName, it.toString())
            SyncDataToFirebase(it)
        })


        fab_btn_add.setSafeOnClickListener {
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

    private fun SyncDataToFirebase(tasksList: List<UserWithComments>?) {
        val extras = intent.extras
        extras?.let {
            val userUid = it.getString(Constants.UID)
            userUid?.let {
                //toast(it)
                database.child(Constants.USERS).child(userUid).setValue(tasksList).addOnCompleteListener {
                    if(it.isSuccessful){ toast("done") }
                    toast(it.result.toString())
                    toast(it.exception?.message.toString())
                }
            }
        }

    }

    private fun initFirebase() {
        database = FirebaseDatabase.getInstance().reference
    }

    private fun showEmptyView() {
        iv_empty.visibility = View.VISIBLE
        tv_no_tasks_title.visibility = View.VISIBLE
    }

    private fun hideEmptyView() {
        iv_empty.visibility = View.GONE
        tv_no_tasks_title.visibility = View.GONE
    }

    private fun stopLoading() {
        rotateloading.stop()
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
        showLoading()
        homeViewModel.getTasks()
    }

    private fun showLoading() {
        rotateloading.start()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.action_all_tasks -> getTasks()
            R.id.action_done_tasks -> {
                taskAdapter.submitList(doneList)
            }
            R.id.action_sync -> showAlertDialog(resources.getString(R.string.alert_message_sync)) {
                homeViewModel.getAllUsersWithComments()
            }
        }
        return true
    }

    override fun onItemSelected(position: Int, item: TaskTable) {
        Log.i(javaClass.simpleName, item.toString())
        Navigation.goToDetailsActivity(this, item)
    }
    override fun onCheckSelected(btn: CompoundButton, isDone: Boolean, item: TaskTable) {
        val newItem = TaskTable(item.id, item.title, item.date, isDone, item.difficulty)
        homeViewModel.updateDoneTask(newItem)
        onResume()
    }
    override fun onIndicatorChecked(position: Int, item: TaskTable) {
        homeViewModel.updatePeriorityTask(item.id, position)
        onResume()
    }
}