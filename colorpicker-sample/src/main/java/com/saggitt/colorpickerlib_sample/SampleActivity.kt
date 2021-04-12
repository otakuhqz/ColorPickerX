/*
 *  This file is part of ColorPickerX
 *  Copyright (c) 2021   Saul Henriquez
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.saggitt.colorpickerlib_sample

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.saggitt.colorpickerx.ColorPicker
import com.saggitt.colorpickerx.ColorPickerTab
import com.saggitt.colorpickerx.OnChooseColorListener
import java.util.*

class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        val dialogButton = findViewById<MaterialButton>(R.id.dialog_button)
        dialogButton.setOnClickListener {
            val colorPicker = ColorPicker(this@SampleActivity)
            val colors = ArrayList<String>()
            colors.add("#FFF44336")
            colors.add("#FFE91E63")
            colors.add("#FFFF2C93")
            colors.add("#FF9C27B0")
            colors.add("#FF673AB7")
            colors.add("#FF3F51B5")
            colors.add("#FF2196F3")
            colors.add("#FF03A9F4")
            colors.add("#FF00BCD4")
            colors.add("#FF009688")
            colors.add("#FF4CAF50") // GREEN 500
            colors.add("#FF8BC34A") // LIGHT GREEN 500
            colors.add("#FFCDDC39") // LIME 500
            colors.add("#FFFFEB3B") // YELLOW 500
            colors.add("#FFFFC107") // AMBER 500
            colors.add("#FFFF9800") // ORANGE 500
            colors.add("#FF795548") // BROWN 500
            colors.add("#FF607D8B") // BLUE GREY 500
            colors.add("#FF9E9E9E") // GREY 500
            colorPicker
                    .setDefaultColorButton(Color.parseColor("#FFF44336"))
                    .setColors(colors)
                    .setColumns(5)
                    .setRoundColorButton(true)
                    .setOnChooseColorListener(object : OnChooseColorListener {
                        override fun onChooseColor(position: Int, color: Int) {
                            Log.d("position", "" + position)
                        }

                        override fun onCancel() {}
                    })
                    //.addListenerButton("newButton") { v, position, color -> Log.d("position", "" + position) }.show()
                    .show()
        }

        val dialogButton2 = findViewById<MaterialButton>(R.id.dialog_button_square)
        dialogButton2.setOnClickListener {
            val colorPicker = ColorPicker(this@SampleActivity)
            val colors = ArrayList<String>()
            colors.add("#FFF44336")
            colors.add("#FFE91E63")
            colors.add("#FFFF2C93")
            colors.add("#FF9C27B0")
            colors.add("#FF673AB7")
            colors.add("#FF3F51B5")
            colors.add("#FF2196F3")
            colors.add("#FF03A9F4")
            colors.add("#FF00BCD4")
            colors.add("#FF009688")
            colors.add("#FF4CAF50") // GREEN 500
            colors.add("#FF8BC34A") // LIGHT GREEN 500
            colors.add("#FFCDDC39") // LIME 500
            colors.add("#FFFFEB3B") // YELLOW 500
            colors.add("#FFFFC107") // AMBER 500
            colors.add("#FFFF9800") // ORANGE 500
            colors.add("#FF795548") // BROWN 500
            colors.add("#FF607D8B") // BLUE GREY 500
            colors.add("#FF9E9E9E") // GREY 500
            colorPicker
                    .setDefaultColorButton(Color.parseColor("#FFF44336"))
                    .setColors(colors)
                    .setColumns(5)
                    .setOnChooseColorListener(object : OnChooseColorListener {
                        override fun onChooseColor(position: Int, color: Int) {
                            Log.d("position", "" + position)
                        }

                        override fun onCancel() {}
                    })
                    //.addListenerButton("newButton") { v, position, color -> Log.d("position", "" + position) }.show()
                    .show()
        }

        val tabbedButton = findViewById<MaterialButton>(R.id.tab_dialog_button)
        tabbedButton.setOnClickListener{
            val colorPicker = ColorPickerTab(this@SampleActivity)
            colorPicker.showAlpha(true)
            val colors = ArrayList<String>()
            colors.add("#FFF44336")
            colors.add("#FFE91E63")
            colors.add("#FFFF2C93")
            colors.add("#FF9C27B0")
            colors.add("#FF673AB7")
            colors.add("#FF3F51B5")
            colors.add("#FF2196F3")
            colors.add("#FF03A9F4")
            colors.add("#FF00BCD4")
            colors.add("#FF009688")
            colors.add("#FF4CAF50") // GREEN 500
            colors.add("#FF8BC34A") // LIGHT GREEN 500
            colors.add("#FFCDDC39") // LIME 500
            colors.add("#FFFFEB3B") // YELLOW 500
            colors.add("#FFFFC107") // AMBER 500
            colors.add("#FFFF9800") // ORANGE 500
            colors.add("#FF795548") // BROWN 500
            colors.add("#FF607D8B") // BLUE GREY 500
            colors.add("#FF9E9E9E") // GREY 500
            colorPicker
                    .setDefaultColorButton(Color.parseColor("#FFF44336"))
                    .setColors(colors)
                    .setColumns(5)
                    .setOnChooseColorListener(object : OnChooseColorListener {
                        override fun onChooseColor(position: Int, color: Int) {
                            Log.d("position", "" + color)
                        }

                        override fun onCancel() {}
                    })
                    .show()
        }
    }
}