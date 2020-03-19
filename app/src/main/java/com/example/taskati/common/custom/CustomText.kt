package com.example.taskati.common.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.taskati.R

class CustomText(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    init {
        inflate(context, R.layout.custom_text, this)
        val titleTab: TextView = findViewById(R.id.tv_title)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomTitles)
        titleTab.text = attributes.getString(R.styleable.CustomTitles_Title)
        attributes.recycle()
    }
}