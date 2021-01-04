package com.mv.test_app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mv.colorpicker.ColorPickerView
import com.mv.colorpicker.DefPalette

class MainActivity : AppCompatActivity(), ColorPickerView.OnColorChangedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(findViewById<ColorPickerView>(R.id.colorPicker)) {
            colorChangedListener = this@MainActivity
            populate(DefPalette().getPalette())
        }
    }

    override fun onColorChanged(color: Int) {
        findViewById<View>(R.id.root).setBackgroundColor(color)
    }
}