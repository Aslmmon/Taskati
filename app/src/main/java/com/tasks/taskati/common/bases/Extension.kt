package com.tasks.taskati.common.bases

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.tasks.taskati.R
import com.google.android.material.datepicker.MaterialDatePicker
import com.homyapplication.common.bases.SafeClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun ViewModel.launchDataLoad(
    execution: suspend CoroutineScope.() -> Unit,
    errorReturned: suspend CoroutineScope.(Throwable) -> Unit,
    finallyBlock: (suspend CoroutineScope.() -> Unit)? = null) {

    GlobalScope.launch {
        try {
            execution()
        } catch (e: Throwable) {
            errorReturned(e)
        } finally {
            finallyBlock?.invoke(this)

        }
    }
}
fun AppCompatActivity.showDatePickerDialog(launchFunction: (String) -> Unit) {
    val builder = MaterialDatePicker.Builder.datePicker()
    val picker = builder.build()
    picker.show(supportFragmentManager,picker.toString())
    picker.addOnPositiveButtonClickListener {
        launchFunction(picker.headerText)
    }
}



fun Context.showAlertDialog(title:String ,launchFunction: () -> Unit) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(resources.getString(R.string.alert_title))
    builder.setMessage(title)
    builder.setPositiveButton(resources.getString(R.string.yes)) { dialog, which ->
        launchFunction()
    }
    builder.setNeutralButton(resources.getString(R.string.no)) { dialog, which ->
        dialog.dismiss()
    }
    val dialog: AlertDialog = builder.create()
    dialog.show()
}




fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}
fun Context.hideKeyboard(){
    val imm= getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}