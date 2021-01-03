package com.mv.test_app

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mv.colorpicker.ColorPickerView

class MainActivity : AppCompatActivity(), ColorPickerView.OnColorChangedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ColorPickerView>(R.id.colorPicker).colorChangedListener = this

        findViewById<View>(R.id.root).setOnClickListener {
            Log.d("123123", " 1234324324")
        }
    }

    override fun onColorChanged(color: Int) {
        findViewById<View>(R.id.root).setBackgroundColor(color)
    }
}