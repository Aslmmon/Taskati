package com.example.taskati.features.login.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.taskati.R
import com.example.taskati.common.Constants
import com.example.taskati.common.bases.setSafeOnClickListener
import com.example.taskati.common.bases.showAlertDialog
import com.example.taskati.common.data.db.TaskTable
import com.example.taskati.common.data.db.comments_table.CommentsTable
import com.example.taskati.features.login.details.adapter.CommentsAdapter
import com.example.taskati.features.login.home.HomeViewModel
import com.example.taskati.features.login.home.adapter.TaskAdapter
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
        }

//        detailViewModel.commentSaved.observe(this, Observer {
//            if (it) {
//                onResume()
//                clearEditText()
//            }
//        })
        detailViewModel.deletedResponse.observe(this, Observer {
            if (it) toast(it.toString())
        })

//        detailViewModel.commentsResponse.observe(this, Observer {
//            Log.i(javaClass.simpleName, it.toString())
//            commentsAdapter.submitList(it)
//        })

        iv_send_btn.setSafeOnClickListener {
            val comment = et_add_comment.text.toString()
            val commentTable = CommentsTable(0, task.id, comment)
            detailViewModel.saveComments(commentTable)
        }


    }

    private fun clearEditText() {
        et_add_comment.text.clear()
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getComments(task.id)

    }

    private fun initRecycler() {
        rv_comments.apply {
            commentsAdapter = CommentsAdapter(this@DetailsActivity)
            adapter = commentsAdapter
        }
    }

    private fun bindDataToViews(dataRecieved: TaskTable) {
        supportActionBar?.title = dataRecieved.title
        supportActionBar?.setHomeButtonEnabled(true)
        tv_date.text = dataRecieved.date
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.action_delete -> showAlertDialog {
                toast(task.title)
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
