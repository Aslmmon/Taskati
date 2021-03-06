package com.tasks.taskati.features.details

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.tasks.taskati.R
import com.tasks.taskati.common.Constants
import com.tasks.taskati.common.bases.setSafeOnClickListener
import com.tasks.taskati.common.bases.showAlertDialog
import com.tasks.taskati.common.bases.showDatePickerDialog
import com.tasks.taskati.common.data.db.TaskTable
import com.tasks.taskati.common.data.db.comments_table.CommentsTable
import com.tasks.taskati.features.details.adapter.CommentsAdapter
import kotlinx.android.synthetic.main.activity_details.*
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel


class DetailsActivity : AppCompatActivity(R.layout.activity_details), CommentsAdapter.Interaction {

    private val detailViewModel: DetailsViewModel by viewModel()
    lateinit var task: TaskTable
    lateinit var commentsAdapter: CommentsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecycler()

        val intent = intent.extras
        intent?.let {
            val dataRecieved = it.getSerializable(Constants.TASK_DETAILS) as TaskTable
            task = dataRecieved
            Log.i(javaClass.simpleName, dataRecieved.toString())
            bindDataToViews(dataRecieved)
            getComments()

        }
        tv_date.setOnClickListener() {
            showDatePickerDialog { newDate ->
                tv_date.text = newDate
                val newItem = TaskTable(task.id, task.title, newDate, task.isDone, task.difficulty)
                detailViewModel.updateDoneTask(newItem)
            }
        }



        check_done.setOnCheckedChangeListener { buttonView, isChecked ->
            val newItem = TaskTable(task.id, task.title, task.date, isChecked, task.difficulty)
            detailViewModel.updateDoneTask(newItem)
        }
        toggle_multi.setOnValueChangedListener { value ->
            val newItem = TaskTable(task.id, task.title, task.date, task.isDone, value)
            detailViewModel.updateDoneTask(newItem)
        }
        detailViewModel.commentSaved.observe(this, Observer {
            getComments()
        })
        iv_send_btn.setSafeOnClickListener {
            val comment = et_add_comment.text?.trim().toString()
            if(comment.isNotEmpty()) {
                val commentTable = CommentsTable(0, task.id, comment)
                detailViewModel.saveComments(commentTable)
                clearEditText()
            }else toast("empty Message")
        }

        detailViewModel.commentsResponse.observe(this, Observer {
            Log.i(javaClass.simpleName, it.toString())
            commentsAdapter.submitList(it[0].comments)
        })

    }

    private fun getComments() {
        detailViewModel.getComments(task.id)

    }

    private fun clearEditText() {
        et_add_comment.text?.clear()
    }

    private fun initRecycler() {
        rv_comments.apply {
            commentsAdapter = CommentsAdapter(this@DetailsActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@DetailsActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = commentsAdapter
        }
    }

    private fun bindDataToViews(dataRecieved: TaskTable) {
        supportActionBar?.title = dataRecieved.title
        supportActionBar?.setHomeButtonEnabled(true)
        tv_date.text = dataRecieved.date
        check_done.isChecked = dataRecieved.isDone
        toggle_multi.value = dataRecieved.difficulty

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.action_delete -> showAlertDialog(resources.getString(R.string.alert_message)) {
                detailViewModel.deleteTask(task)
                finish()
            }
            android.R.id.home -> finish()
        }
        return true
    }

    override fun onItemSelected(position: Int, item: CommentsTable) {

    }

}
