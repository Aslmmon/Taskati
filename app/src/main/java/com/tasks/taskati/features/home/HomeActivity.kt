package com.tasks.taskati.features.home

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.tasks.taskati.R
import com.tasks.taskati.common.Constants
import com.tasks.taskati.common.Navigation
import com.tasks.taskati.common.bases.setSafeOnClickListener
import com.tasks.taskati.common.bases.showAlertDialog
import com.tasks.taskati.common.data.db.TaskTable
import com.tasks.taskati.common.model.UserWithComments
import com.tasks.taskati.features.home.adapter.TaskAdapter
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
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
    var auth = FirebaseAuth.getInstance()

    lateinit var doneList: List<TaskTable>
    private lateinit var database: DatabaseReference
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
        homeViewModel.Error.observe(this, Observer {
            toast(it)
        })

        homeViewModel.taskUpdated.observe(this, Observer {
            if (it) getTasks()
        })

        homeViewModel.allTasksWithComments.observe(this, Observer {
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
            userUid?.let { user ->
                if (Navigation.isNetworkAvailable(this)) {
                    if (tasksList?.isEmpty()!!) {
                        toast(resources.getString(R.string.no_tasks))
                        return
                    }
                    showLoading()
                    saveToFirebaseDatabase(user, tasksList)
                } else toast(resources.getString(R.string.check_connection))
            }
        }

    }

    private fun saveToFirebaseDatabase(
        userUid: String,
        tasksList: List<UserWithComments>?
    ): Task<Void> {
        return database.child(Constants.USERS).child(userUid).setValue(tasksList)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    stopLoading()
                    toast(resources.getString(R.string.sync_to_database))
                } else {
                    stopLoading()
                    toast(it.exception?.message.toString())
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

    private fun stopLoading() {
        rotateloading.stop()
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
            R.id.action_sign_out -> showAlertDialog(resources.getString(R.string.sign_out_message)){
                auth.signOut()
                Navigation.goToLoginActivityWithClearTasks(this)
            }
        }
        return true
    }

    override fun onItemSelected(position: Int, item: TaskTable) {
        Log.i(javaClass.simpleName, item.toString())
        Navigation.goToDetailsActivity(this, item)
    }

    override fun onCheckSelected(btn: CompoundButton, isDone: Boolean, item: TaskTable) {
        val newTask = TaskTable(item.id, item.title, item.date, isDone, item.difficulty)
        homeViewModel.updateDoneTask(newTask)
    }

    override fun onIndicatorChecked(position: Int, item: TaskTable) {
        val newTask = TaskTable(item.id, item.title, item.date, item.isDone, position)
        homeViewModel.updateDoneTask(newTask)

    }
}