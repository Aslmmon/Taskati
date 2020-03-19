package com.example.taskati.features.login.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskati.common.Repo.DetailRepo.IDetail
import com.example.taskati.common.data.db.TaskTable
import com.example.taskati.common.data.db.comments_table.CommentsTable
import kotlinx.coroutines.launch

class DetailsViewModel(var detailRepo: IDetail) : ViewModel() {


    private val _deleteTask = MutableLiveData<Boolean>()
    val deleteTask: LiveData<Boolean>
        get() = _deleteTask

    private val _taskSaved = MutableLiveData<Boolean>()
    val taskSaved: LiveData<Boolean>
        get() = _taskSaved


    private val _commentsResponse = MutableLiveData<List<CommentsTable>>()
    val commentsResponse: LiveData<List<CommentsTable>>
        get() = _commentsResponse

    fun saveComments(comment: CommentsTable) {
        viewModelScope.launch {
            try {
                Log.i(javaClass.simpleName, "save Comment")
                detailRepo.saveCommentToTask(comment)
            } catch (t: Throwable) {
                Log.i(javaClass.simpleName, t.message)
            }
        }
    }


    fun getComments(userId:Int) {
        viewModelScope.launch {
            try {
                Log.i(javaClass.simpleName, "All Comment")
                _commentsResponse.postValue(detailRepo.getComments(userId))
            } catch (t: Throwable) {
                Log.i(javaClass.simpleName, t.message)
            }
        }
    }


    fun deleteTask(task: TaskTable) {
        viewModelScope.launch {
            try {
                Log.i(javaClass.simpleName, "deleted")
                detailRepo.deleteTask(task.id)
                //   _deleteTask.postValue(true)
            } catch (t: Throwable) {
                Log.i(javaClass.simpleName, t.message)
            }
        }
    }

}