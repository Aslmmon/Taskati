package com.example.taskati.common.bases

import android.app.AlertDialog
import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.taskati.R
import com.google.android.material.datepicker.MaterialDatePicker
import com.homyapplication.common.bases.SafeClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//package com.floriaapp.vendor.common.bases
//
//import android.app.Activity
//import android.app.Dialog
//import android.content.Context
//import android.content.DialogInterface
//import android.graphics.Color
//import android.graphics.drawable.ColorDrawable
//import android.text.Editable
//import android.view.View
//import android.view.ViewGroup
//import android.view.Window
//import android.widget.ImageView
//import android.widget.RelativeLayout
//import androidx.constraintlayout.widget.ConstraintSet
//import androidx.constraintlayout.widget.Constraints
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.floriaapp.core.domain.models.error_response.ErrorResponse
//import com.floriaapp.core.domain.models.new_order_response.Data
//import com.floriaapp.vendor.R
//import com.floriaapp.vendor.common.utils.Consts
//import com.google.android.material.snackbar.Snackbar
//import com.google.gson.GsonBuilder
//import com.homyapplication.common.bases.SafeClickListener
//import com.squareup.picasso.Picasso
//import com.victor.loading.rotate.RotateLoading
//import kotlinx.android.synthetic.main.activity_forget_pass.view.*
//import kotlinx.android.synthetic.main.loading_progress.*
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.launch
//import retrofit2.HttpException
//import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView
//import java.net.ConnectException
//import java.net.SocketTimeoutException
//import java.net.UnknownHostException
//
//
//fun Any?.notNull(f: () -> String) {
//    if (this != null) {
//        f()
//    }
//}
//
//fun Context.showCaseOfSingleView(target: View) {
//    MaterialShowcaseView.Builder(this as Activity)
//            .setTarget(target)
//            .setDismissText(resources.getString(R.string.dismiss_text_showCase))
//            .singleUse(Consts.SHOW_CASE_SEQUENCE)
//            .setContentText(R.string.change_order_status_showCase)
//            .build().show(this)
//}
//
//
//fun Context.getCompleteAddress(data: Data?): String {
//    val address = data?.address?.get(0)
//    val completeAddres: String
//    if (address?.apartmentNumber != null) completeAddres = address.name + "-" + address.district.name + "-" + "${address.apartmentNumber}"
//    else completeAddres = address?.name + "-" + "${address?.district?.name}"
//    return completeAddres
//}
//
//fun Throwable?.toErrorBody(): String {
//    val s = when (this) {
//        is SocketTimeoutException -> " Check Your Network Connection , Try Again later "
//        is ConnectException -> " Check Your Network Connection , Try Again later "
//        is UnknownHostException -> message.toString() + " Try again later "
//        is HttpException -> {
//            val errorBodyResponse = response()?.errorBody()?.string()
//            val gson = GsonBuilder().create()
//            val error = gson.fromJson<ErrorResponse>(errorBodyResponse, ErrorResponse::class.java)
//            when (error.httpCode) {
//                422 -> {
//                    val mobileError = error.error.message.mobile?.get(0)
//                    val passwordError = error.error.message.password?.get(0)
//                    val curentPasswordError = error.error.message.currentPassword?.get(0)
//                    val confirmPassword = error.error.message.confirmPassword?.get(0)
//                    mobileError?.let { mobileError -> return mobileError }
//                    passwordError?.let { passwordError -> return passwordError }
//                    curentPasswordError?.let { curentPasswordError -> return curentPasswordError }
//                    confirmPassword?.let { confirmPassword -> return confirmPassword }
//                }
//                400 -> {
//                    val bodyError = error.error.message.body?.get(0)
//                    return bodyError.toString()
//                }
//                401 -> {
//                    // unAuthenticated
//                    val bodyError = error.error.message.body?.get(0)
//                    return bodyError.toString()
//                }
//                403 -> {
//                    // unAuthroized
//                    val bodyError = error.error.message.body?.get(0)
//                    return bodyError.toString()
//                }
//                else -> message()
//            }
//        }
//        else -> this?.message
//    }
//    return this?.message.toString()
//}
//
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

////
////fun NotificationManager.sendNotification(messageBody: String, messageTitle: String, applicationContext: Context) {
////    val contentIntent = Intent(applicationContext, OrdersHistoryActivity::class.java)
////    contentIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
////    contentIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
////    val pendingIntent = PendingIntent.getActivity(
////            applicationContext, 5, contentIntent,
////            PendingIntent.FLAG_UPDATE_CURRENT
////    )
////
////    val builder = NotificationCompat.Builder(
////            applicationContext,
////            applicationContext.getString(R.string.notification_id)
////    ).setContentTitle((messageTitle))
////            .setContentText(messageBody)
////            .setContentIntent(pendingIntent)
////            .setLargeIcon(BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_logo))
////            .setSmallIcon(R.drawable.ic_logo)
////            .setAutoCancel(true)
////            .setPriority(NotificationCompat.PRIORITY_HIGH)
////
////    notify(5, builder.build())
////}
//
//fun Context.showLoading() {
//    val constraintSet = ConstraintSet()
//    val rotate = RotateLoading(this)
//    rotate.id = R.id.loading
//    rotate.start()
//
//
//}
//
//fun Context.removeLoading() {
//    val rotate = RotateLoading(this)
//    rotate.id = R.id.loading
//    rotate.stop()
//}
//
//fun Context.showLargeImage(urlOfImage: String) {
//    val builder = Dialog(this)
//    builder.requestWindowFeature(Window.FEATURE_NO_TITLE)
//    builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//    builder.setCanceledOnTouchOutside(true)
//    val imageView = ImageView(this)
//    Picasso.get().load(urlOfImage).into(imageView)
//    builder.addContentView(imageView, RelativeLayout.LayoutParams(
//            ViewGroup.LayoutParams.WRAP_CONTENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT))
//    builder.show()
//}
//
//
//
//
