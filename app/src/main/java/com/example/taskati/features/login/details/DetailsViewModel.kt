package com.example.taskati.features.login.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskati.common.Repo.DetailRepo.IDetail
import com.example.taskati.common.Repo.HomeRepo.HomeRepo
import com.example.taskati.common.Repo.HomeRepo.IHome
import com.example.taskati.common.bases.launchDataLoad
import com.example.taskati.common.data.db.TaskTable
import com.example.taskati.common.data.db.comments_table.CommentsTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class DetailsViewModel(var detailRepo: IDetail,var homeRepo:IHome) : ViewModel() {


    private val _commentSaved = MutableLiveData<Boolean>()
    val commentSaved: LiveData<Boolean>
        get() = _commentSaved


    private val _commentsResponse = MutableLiveData<List<CommentsTable>>()
    val commentsResponse: LiveData<List<CommentsTable>>
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

    fun updateDoneTask(userId: Int, doneTask: Boolean) {
        launchDataLoad(execution = {
         //   homeRepo.updateDoneTask(userId, doneTask)
            Log.i(javaClass.simpleName, "Updated ")
        }, errorReturned = {
            Log.i(javaClass.simpleName, it.message)
        })
    }

//    fun saveComments(comment: CommentsTable) {
//        viewModelScope.launch {
//            try {
//                Log.i(javaClass.simpleName, "save Comment")
//                detailRepo.saveCommentToTask(comment)
//                _commentSaved.postValue(true)
//            } catch (t: Throwable) {
//                Log.i(javaClass.simpleName, t.message)
//            }
//        }
//    }

//
//    fun getComments(userId: Int) {
//        viewModelScope.launch {
//            try {
//                _commentsResponse.postValue(detailRepo.getComments())
//                Log.i(javaClass.simpleName, "All Comment")
//            } catch (t: Throwable) {
//                Log.i(javaClass.simpleName, t.message)
//            }
//        }
//    }


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