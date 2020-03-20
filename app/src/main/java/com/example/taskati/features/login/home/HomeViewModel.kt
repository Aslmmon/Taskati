package com.example.taskati.features.login.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskati.common.Repo.HomeRepo.IHome
import com.example.taskati.common.bases.launchDataLoad
import com.example.taskati.common.data.db.TaskTable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel(var homeRepo: IHome) : ViewModel() {

    private val _tasksResponse = MutableLiveData<List<TaskTable>>()
    val tasksResponse: LiveData<List<TaskTable>>
        get() = _tasksResponse

    private val _updateTaskResponse = MutableLiveData<Boolean>()
    val updateTask: LiveData<Boolean>
        get() = _updateTaskResponse

    private val _updatePeriority = MutableLiveData<Boolean>()
    val updatePeriority: LiveData<Boolean>
        get() = _updatePeriority


    private val _taskSaved = MutableLiveData<Boolean>()
    val taskSaved: LiveData<Boolean>
        get() = _taskSaved


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
            homeRepo.updateDoneTask(userId, doneTask)
            _updateTaskResponse.postValue(true)
            Log.i(javaClass.simpleName, "Updated ")
        }, errorReturned = {
            Log.i(javaClass.simpleName, it.message)
        })
    }


    fun getTasks() {
        launchDataLoad(execution = {
            _tasksResponse.postValue(homeRepo.getTasks())
        }, errorReturned = {
            Log.i(javaClass.simpleName, it.message)
        })
    }

    fun saveTask(task: TaskTable) {

        launchDataLoad(execution = {
            homeRepo.saveToDatabase(task)
            _taskSaved.postValue(true)
            Log.i(javaClass.simpleName, "saved")
        }, errorReturned = {
            Log.i(javaClass.simpleName, it.message)
        })
    }

}
