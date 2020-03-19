package com.example.taskati.features.login.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskati.common.Repo.DetailRepo.IDetail
import com.example.taskati.common.data.db.TaskTable
import kotlinx.coroutines.launch

class DetailsViewModel(var detailRepo: IDetail) : ViewModel() {


    private val _deleteTask = MutableLiveData<Boolean>()
    val deleteTask: LiveData<Boolean>
        get() = _deleteTask

    private val _taskSaved = MutableLiveData<Boolean>()
    val taskSaved: LiveData<Boolean>
        get() = _taskSaved


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