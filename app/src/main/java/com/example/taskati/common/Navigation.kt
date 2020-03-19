package com.example.taskati.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.taskati.features.login.home.HomeActivity

object Navigation {

    fun goToHomeActivity(ctx: Context) {
        ctx.startActivity(Intent(ctx, HomeActivity::class.java))
        (ctx as Activity).finish()
    }
}