package com.tasks.taskati.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.tasks.taskati.common.data.db.TaskTable
import com.tasks.taskati.features.login.LoginActivity
import com.tasks.taskati.features.login.details.DetailsActivity
import com.tasks.taskati.features.login.home.HomeActivity

object Navigation {

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
    }
    fun goToHomeActivity(ctx: Context,uid:String) {
        val intent = Intent(ctx, HomeActivity::class.java)
        intent.putExtra(Constants.UID,uid)
        ctx.startActivity(intent)
        (ctx as Activity).finish()
    }
    fun goToLoginActivity(ctx: Context) {
        val intent = Intent(ctx, LoginActivity::class.java)
        ctx.startActivity(intent)
        (ctx as Activity).finish()
    }

    fun goToDetailsActivity(ctx: Context,task:TaskTable) {
        val intent = Intent(ctx, DetailsActivity::class.java)
        intent.putExtra(Constants.TASK_DETAILS ,task)
        ctx.startActivity(intent)
    }
}