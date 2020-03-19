package com.example.taskati.features.login.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taskati.R
import com.example.taskati.common.bases.setSafeOnClickListener
import com.example.taskati.common.data.db.TaskTable
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity(R.layout.activity_home) {

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fab_btn_add.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.taks_bottom_sheet, null)
            val create_btn = view.findViewById<MaterialButton>(R.id.btn_create_task)
            create_btn.setSafeOnClickListener {
                dialog.dismiss()
                val task = TaskTable(date = "1",difficulty = 1,isDone = false,title= "eshta")

                homeViewModel.saveTask(task)
            }
            dialog.setContentView(view)
            dialog.show()
        }
    }
}
