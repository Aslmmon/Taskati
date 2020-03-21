package com.example.taskati.features.login.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskati.common.Repo.DetailRepo.IDetail
import com.example.taskati.common.Repo.HomeRepo.IHome
import com.example.taskati.common.bases.launchDataLoad
import com.example.taskati.common.data.db.TaskTable
import com.example.taskati.common.data.db.comments_table.CommentsTable
import com.example.taskati.common.model.UserWithComments
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailsViewModel(var detailRepo: IDetail,var homeRepo:IHome) : ViewModel() {


    private val _commentSaved = MutableLiveData<Boolean>()
    val commentSaved: LiveData<Boolean>
        get() = _commentSaved


    private val _commentsResponse = MutableLiveData<List<UserWithComments>>()
    val commentsResponse: LiveData<List<UserWithComments>>
        get() = _commentsResponse


    private val _deletedResponse = MutableLiveData<Boolean>()
    val deletedResponse: LiveData<Boolean>
        get() = _deletedResponse


    fun updatePeriorityTask(userId: Int, periority: Int) {
        launchDataLoad(execution = {
            homeRepo.updatePeriorityTask(userId, periority)
            Log.i(javaClass.simpleName, "Updated Indicator")
        }, errorReturned = {
            Log.i(javaClass.simpleName, it.message)
        })
    }

    fun updateDoneTask(doneTask:TaskTable) {
        launchDataLoad(execution = {
         homeRepo.updateDoneTask(doneTask)
            Log.i(javaClass.simpleName, "Updated ")
        }, errorReturned = {
            Log.i(javaClass.simpleName, it.message)
        })
    }

    fun saveComments(comment: CommentsTable) {
        GlobalScope.launch {
            try {
                Log.i(javaClass.simpleName, "save Comment")
                detailRepo.saveCommentToTask(comment)
                _commentSaved.postValue(true)
            } catch (t: Throwable) {
                Log.i(javaClass.simpleName, t.message)
            }
        }
    }


    fun getComments(userId: Int) {
        GlobalScope.launch {
            try {
                _commentsResponse.postValue(detailRepo.getUserWithComments(userId))
                Log.i(javaClass.simpleName, "All Comment")
            } catch (t: Throwable) {
                Log.i(javaClass.simpleName, t.message)
            }
        }
    }


    fun deleteTask(task: TaskTable) {
        GlobalScope.launch {
            try {
                detailRepo.deleteTask(task)
            } catch (t: Throwable) {
                Log.i(javaClass.simpleName, t.message)
            }
        }
    }

}