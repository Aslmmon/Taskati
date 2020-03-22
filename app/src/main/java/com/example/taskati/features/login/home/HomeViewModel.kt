package com.example.taskati.features.login.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskati.common.Repo.HomeRepo.IHome
import com.example.taskati.common.bases.launchDataLoad
import com.example.taskati.common.data.db.TaskTable
import com.example.taskati.common.model.UserWithComments
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel(var homeRepo: IHome) : ViewModel() {

    private val _tasksResponse = MutableLiveData<List<TaskTable>>()
    val tasksResponse: LiveData<List<TaskTable>>
        get() = _tasksResponse

    private val _allTasksWithComments = MutableLiveData<List<UserWithComments>>()
    val allTasksWithComments: LiveData<List<UserWithComments>>
        get() = _allTasksWithComments


    private val _taskUpdated = MutableLiveData<Boolean>()
    val taskUpdated: LiveData<Boolean>
        get() = _taskUpdated

    private val _error = MutableLiveData<String>()
    val Error: LiveData<String>
        get() = _error



    fun updateDoneTask(doneTask: TaskTable) {
        launchDataLoad(execution = {
            homeRepo.updateDoneTask(doneTask)
            _taskUpdated.postValue(true)
        }, errorReturned = {
            _taskUpdated.postValue(false)
            _error.postValue(it.message)
        })
    }


    fun getTasks() {
        launchDataLoad(execution = {
            _tasksResponse.postValue(homeRepo.getTasks())
        }, errorReturned = {
            _error.postValue(it.message)
        })
    }

    fun saveTask(task: TaskTable) {
        launchDataLoad(execution = {
            homeRepo.saveToDatabase(task)
            _taskUpdated.postValue(true)
        }, errorReturned = {
            _taskUpdated.postValue(false)
            _error.postValue(it.message)
        })
    }

    fun getAllUsersWithComments() {
        launchDataLoad(execution = {
            homeRepo.getUsersWithComments()
            _allTasksWithComments.postValue(homeRepo.getUsersWithComments())
        }, errorReturned = {
            _error.postValue(it.message)
        })
    }


}
