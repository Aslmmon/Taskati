package com.example.taskati.common//package com.example.taskati.common
//
//import android.annotation.SuppressLint
//import android.app.NotificationManager
//import android.util.Log
//import androidx.core.content.ContextCompat
//import com.example.android.eggtimernotifications.util.sendNotification
//import com.floriaapp.vendor.App.Companion.context
//import com.floriaapp.vendor.common.utils.SaveSharedPreference
//import com.google.firebase.messaging.FirebaseMessagingService
//import com.google.firebase.messaging.RemoteMessage
//
//class NotificationService : FirebaseMessagingService() {
//
//    @SuppressLint("CheckResult")
//    override fun onNewToken(token: String) {
//        SaveSharedPreference.saveNotificationToken(this, token)
//        super.onNewToken(token)
//    }
//
//
//    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//      //  remoteMessage.mes == "general"
//
//        remoteMessage.notification?.let {
//
//
//            Log.i(javaClass.simpleName, "Message Notification Body: ${it.body}")
//            Log.i(javaClass.simpleName, "Message Notification title: ${it.title}")
//            val notificationManager = ContextCompat.getSystemService(context, NotificationManager::class.java) as NotificationManager
//            notificationManager.sendNotification(it.body.toString(), it.title.toString(), context)
//        }
//    }
//}