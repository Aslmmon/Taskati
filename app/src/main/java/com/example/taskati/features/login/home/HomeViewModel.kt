package com.example.taskati.features.login.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskati.common.Repo.HomeRepo.IHome
import com.example.taskati.common.data.db.TaskTable
import kotlinx.coroutines.launch

class HomeViewModel(var homeRepo: IHome) : ViewModel() {

    private val _tasksResponse = MutableLiveData<List<TaskTable>>()
    val tasksResponse: LiveData<List<TaskTable>>
        get() = _tasksResponse

    private val _updateTaskResponse = MutableLiveData<Boolean>()
    val updateTask: LiveData<Boolean>
        get() = _updateTaskResponse


    private val _taskSaved = MutableLiveData<Boolean>()
    val taskSaved: LiveData<Boolean>
        get() = _taskSaved



    fun updatePeriorityTask(userId: Int, periority: Int) {
        viewModelScope.launch {
            try {
                homeRepo.updatePeriorityTask(userId,periority)
                Log.i(javaClass.simpleName, "Updated Indicator")
            } catch (t: Throwable) {
                Log.i(javaClass.simpleName, t.message)
            }
        }
    }

    fun updateDoneTask(userId: Int, doneTask: Boolean) {
        viewModelScope.launch {
            try {
                homeRepo.updateDoneTask(userId, doneTask)
                _updateTaskResponse.postValue(true)
                Log.i(javaClass.simpleName, "Updated ")

            } catch (t: Throwable) {
                Log.i(javaClass.simpleName, t.message)
            }
        }
    }


    fun getTasks() {
        viewModelScope.launch {
            try {
                _tasksResponse.postValue(homeRepo.getTasks())
            } catch (t: Throwable) {
                Log.i(javaClass.simpleName, t.message)
            }
        }
    }

    fun saveTask(task: TaskTable) {
        viewModelScope.launch {
            try {
                homeRepo.saveToDatabase(task)
                _taskSaved.postValue(true)
                Log.i(javaClass.simpleName, "saved")
            } catch (t: Throwable) {
                Log.i(javaClass.simpleName, t.message)
            }
        }
    }

}
