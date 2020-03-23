package com.tasks.taskati.features.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.tasks.taskati.R
import com.tasks.taskati.common.Navigation
import com.tasks.taskati.common.bases.setSafeOnClickListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.loading.*
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity(R.layout.activity_login) {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeFireBase()


        btn_login.setSafeOnClickListener {
            val name = et_username.text.toString()
            signInWithEmailAndPassword(name)
        }


    }

    private fun signInWithEmailAndPassword(name: String) {
        showLoading()
        auth.signInWithEmailAndPassword("$name@gmail.com", "123456")
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    stopLoading()
                    val user = auth.currentUser
                    user?.let {
                        Navigation.goToHomeActivity(this, it.uid)
                    }
                } else {
                    stopLoading()
                    toast("Authentication failed. ${task.exception?.message}")
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        currentUser?.let {
            Navigation.goToHomeActivity(this,currentUser.uid)
        }
    }

    private fun initializeFireBase() {
        auth = FirebaseAuth.getInstance()
    }
    private fun showLoading() {
        rotateloading.start()
    }

    private fun stopLoading() {
        rotateloading.stop()
    }
}
