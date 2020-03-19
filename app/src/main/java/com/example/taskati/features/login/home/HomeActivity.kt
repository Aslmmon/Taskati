package com.example.taskati.features.login.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taskati.R
import com.example.taskati.common.bases.setSafeOnClickListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(R.layout.activity_home) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fab_btn_add.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.taks_bottom_sheet, null)
            val create_btn = view.findViewById<MaterialButton>(R.id.btn_create_task)
            create_btn.setSafeOnClickListener {
                dialog.dismiss()
            }
            dialog.setContentView(view)
            dialog.show()
        }
    }
}
