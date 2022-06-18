/*
 *  This file is part of ColorPickerX
 *  Copyright (c) 2022   Saul Henriquez
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

package com.shlabs.colorpickerx.sampleapp

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.ui.LocalScaffoldPadding
import com.shlabs.colorpickerx.ColorPicker
import com.shlabs.colorpickerx.ColorPickerTab
import com.shlabs.colorpickerx.ColorSelectorPresets
import com.shlabs.colorpickerx.OnChooseColorListener

class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val searchBarHeight = 56.dp
    val searchBarVerticalMargin = 8.dp
    val innerPadding = remember { MutablePaddingValues() }
    val statusBarHeight = 26.dp
    val contentShift = statusBarHeight + searchBarVerticalMargin + searchBarHeight / 2

    Scaffold(
    ) {
        val layoutDirection = LocalLayoutDirection.current
        innerPadding.left = it.calculateLeftPadding(layoutDirection)
        innerPadding.top = it.calculateTopPadding() - contentShift
        innerPadding.right = it.calculateRightPadding(layoutDirection)
        innerPadding.bottom = it.calculateBottomPadding()
        val context = LocalContext.current

        val colors: ArrayList<String> = arrayListOf()
        colors.addAll(context.resources.getStringArray(R.array.dialog_colors))

        CompositionLocalProvider(
            LocalScaffoldPadding provides innerPadding
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Spacer(modifier = Modifier.height(16.dp))
                //Button 1
                Button(
                    onClick = {
                        val colorPicker = ColorPicker(context)
                            .setDefaultColorButton(android.graphics.Color.parseColor(colors[1]))
                            .setColors(colors)
                            .setColumns(5)
                            .setRoundColorButton(true)
                            .setOnChooseColorListener(object : OnChooseColorListener {
                                override fun onChooseColor(position: Int, color: Int) {
                                    Log.d("position", "" + position)
                                }

                                override fun onCancel() {}
                            })
                        colorPicker.show()
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .height(60.dp)
                        .width(220.dp)
                ) {
                    Image(
                        painterResource(id = R.drawable.ic_email_unread),
                        contentDescription = stringResource(id = R.string.dialog_button_rounded),
                        modifier = Modifier.size(36.dp),
                        colorFilter = ColorFilter.tint(
                            Color(0xFFFFFFFF)
                        )
                    )
                    Text(
                        text = stringResource(id = R.string.dialog_button_rounded),
                        modifier = Modifier.padding(start = 8.dp),
                        fontSize = 18.sp,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                //Button 2
                Button(
                    onClick = {
                        val colorPicker = ColorPicker(context)
                            .setDefaultColorButton(android.graphics.Color.parseColor(colors[1]))
                            .setColors(colors)
                            .setColumns(5)
                            .setOnChooseColorListener(object : OnChooseColorListener {
                                override fun onChooseColor(position: Int, color: Int) {
                                    Log.d("position", "" + position)
                                }

                                override fun onCancel() {}
                            })
                        colorPicker.show()
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .height(60.dp)
                        .width(220.dp)
                ) {
                    Image(
                        painterResource(id = R.drawable.ic_email_unread),
                        contentDescription = stringResource(id = R.string.dialog_button_square),
                        modifier = Modifier.size(36.dp),
                        colorFilter = ColorFilter.tint(
                            Color(0xFFFFFFFF)
                        )
                    )
                    Text(
                        text = stringResource(id = R.string.dialog_button_square),
                        modifier = Modifier.padding(start = 8.dp),
                        fontSize = 18.sp,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                //Button 3
                Button(
                    onClick = {
                        val colorPicker = ColorPickerTab(context)
                        colorPicker
                            .setDefaultColorButton(android.graphics.Color.WHITE)
                            .setColors(colors)
                            .setColumns(5)
                            .showAlpha(false)
                            .setOnChooseColorListener(object : OnChooseColorListener {
                                override fun onChooseColor(position: Int, color: Int) {
                                    Log.d("position", "" + color)
                                }

                                override fun onCancel() {}
                            })
                            .show()
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .height(60.dp)
                        .width(220.dp)
                ) {
                    Image(
                        painterResource(id = R.drawable.ic_email),
                        contentDescription = stringResource(id = R.string.tab_button),
                        modifier = Modifier.size(36.dp),
                        colorFilter = ColorFilter.tint(
                            Color(0xFFFFFFFF)
                        )
                    )
                    Text(
                        text = stringResource(id = R.string.tab_button),
                        modifier = Modifier.padding(start = 8.dp),
                        fontSize = 18.sp,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                //Button 4
                Button(
                    onClick = {
                        val colorPicker = ColorSelectorPresets(context)
                        colorPicker
                            .setDefaultColorButton(android.graphics.Color.RED)
                            .setColumns(4)
                            .setColorButtonSize(48, 48)
                            .setRoundColorButton(true)
                            .setOnChooseColorListener(object : OnChooseColorListener {
                                override fun onChooseColor(position: Int, color: Int) {
                                    Log.d("position", "" + color)
                                }

                                override fun onCancel() {}
                            })
                            .show()
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .height(60.dp)
                        .width(220.dp)
                ) {
                    Image(
                        painterResource(id = R.drawable.ic_email),
                        contentDescription = stringResource(id = R.string.custom_buttom),
                        modifier = Modifier.size(36.dp),
                        colorFilter = ColorFilter.tint(
                            Color(0xFFFFFFFF)
                        )
                    )
                    Text(
                        text = stringResource(id = R.string.custom_buttom),
                        modifier = Modifier.padding(start = 8.dp),
                        fontSize = 18.sp,
                    )
                }

            }
        }
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    MainScreen()
}

@Stable
internal class MutablePaddingValues : PaddingValues {
    var left: Dp by mutableStateOf(0.dp)
    var top: Dp by mutableStateOf(0.dp)
    var right: Dp by mutableStateOf(0.dp)
    var bottom: Dp by mutableStateOf(0.dp)

    override fun calculateLeftPadding(layoutDirection: LayoutDirection) = left

    override fun calculateTopPadding(): Dp = top

    override fun calculateRightPadding(layoutDirection: LayoutDirection) = right

    override fun calculateBottomPadding(): Dp = bottom
}