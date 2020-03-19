package com.example.taskati.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.taskati.features.login.home.HomeActivity

object Navigation {

    fun goToHomeActivity(ctx: Context,uid:String) {
        val intent = Intent(ctx, HomeActivity::class.java)
        intent.putExtra("uid",uid)
        ctx.startActivity(intent)
        (ctx as Activity).finish()
    }
}