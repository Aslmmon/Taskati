package com.example.taskati.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.taskati.common.data.db.TaskTable
import com.example.taskati.features.login.details.DetailsActivity
import com.example.taskati.features.login.home.HomeActivity

object Navigation {

    fun goToHomeActivity(ctx: Context,uid:String) {
        val intent = Intent(ctx, HomeActivity::class.java)
        intent.putExtra(Constants.UID,uid)
        ctx.startActivity(intent)
        (ctx as Activity).finish()
    }

    fun goToDetailsActivity(ctx: Context,task:TaskTable) {
        val intent = Intent(ctx, DetailsActivity::class.java)
        intent.putExtra(Constants.TASK_DETAILS ,task)
        ctx.startActivity(intent)
    }
}