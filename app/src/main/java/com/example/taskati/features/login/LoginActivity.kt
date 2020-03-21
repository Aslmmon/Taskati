package com.example.taskati.features.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.taskati.R
import com.example.taskati.common.Navigation
import com.example.taskati.common.bases.setSafeOnClickListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

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
        auth.signInWithEmailAndPassword("$name@gmail.com", "123456")
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        Navigation.goToHomeActivity(this, it.uid)
                    }
                } else Toast.makeText(baseContext, "Authentication failed. ${task.exception?.message}", Toast.LENGTH_SHORT).show()
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
}
