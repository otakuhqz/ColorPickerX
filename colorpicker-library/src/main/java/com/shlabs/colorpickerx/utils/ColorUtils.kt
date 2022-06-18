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
package com.shlabs.colorpickerx.utils

import android.content.Context
import android.graphics.Color
import androidx.annotation.ColorInt

class ColorUtils {
    /**
     * Returns true if the text color should be white, given a background color
     *
     * @param color background color
     * @return true if the text should be white, false if the text should be black
     */

    companion object {
        fun isWhiteText(@ColorInt color: Int): Boolean {
            val red = Color.red(color)
            val green = Color.green(color)
            val blue = Color.blue(color)

            // https://en.wikipedia.org/wiki/YIQ
            // https://24ways.org/2010/calculating-color-contrast/
            val yiq = (red * 299 + green * 587 + blue * 114) / 1000
            return yiq < 192
        }

        fun getDimensionDp(resID: Int, context: Context): Int {
            return (context.resources.getDimension(resID) / context.resources.displayMetrics.density).toInt()
        }

        fun dip2px(dpValue: Float, context: Context): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        @Throws(NumberFormatException::class)
        fun parseColorString(color: String): Int {
            var colorString = color
            val stringLength = colorString.length

            val a: Int
            var r = 0
            var g = 0
            var b = 0
            if (colorString.startsWith("#")) {
                colorString = colorString.substring(1)
            }

            if (stringLength == 0) {
                a = 255
            } else if (stringLength <= 2) {
                a = 255
                b = colorString.toInt(16)
            } else if (stringLength == 3) {
                a = 255
                r = colorString.substring(0, 1).toInt(16)
                g = colorString.substring(1, 2).toInt(16)
                b = colorString.substring(2, 3).toInt(16)
            } else if (stringLength == 4) {
                a = 255
                g = colorString.substring(0, 2).toInt(16)
                b = colorString.substring(2, 4).toInt(16)
            } else if (stringLength == 5) {
                a = 255
                r = colorString.substring(0, 1).toInt(16)
                g = colorString.substring(1, 3).toInt(16)
                b = colorString.substring(3, 5).toInt(16)
            } else if (stringLength == 6) {
                a = 255
                r = colorString.substring(0, 2).toInt(16)
                g = colorString.substring(2, 4).toInt(16)
                b = colorString.substring(4, 6).toInt(16)
            } else if (stringLength == 7) {
                a = colorString.substring(0, 1).toInt(16)
                r = colorString.substring(1, 3).toInt(16)
                g = colorString.substring(3, 5).toInt(16)
                b = colorString.substring(5, 7).toInt(16)
            } else if (stringLength == 8) {
                a = colorString.substring(0, 2).toInt(16)
                r = colorString.substring(2, 4).toInt(16)
                g = colorString.substring(4, 6).toInt(16)
                b = colorString.substring(6, 8).toInt(16)
            } else {
                b = -1
                g = -1
                r = -1
                a = -1
            }

            return Color.argb(a, r, g, b)
        }
    }
}