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

import android.graphics.*
import android.graphics.drawable.Drawable
import kotlin.math.ceil

class AlphaPatternDrawable(private val rectangleSize: Int) : Drawable() {
    private val paint = Paint()
    private val paintWhite = Paint()
    private val paintGray = Paint()

    private var numRectanglesHorizontal = 0
    private var numRectanglesVertical = 0

    /**
     * Bitmap in which the pattern will be cached.
     * This is so the pattern will not have to be recreated each time draw() gets called.
     * Because recreating the pattern i rather expensive. I will only be recreated if the size changes.
     */
    private var bitmap: Bitmap? = null

    init {
        paintWhite.color = Color.parseColor("#FFFFFFFF")
        paintGray.color = Color.parseColor("#FFCBCBCB")
    }

    override fun draw(canvas: Canvas) {
        if (bitmap != null && !bitmap!!.isRecycled) {
            canvas.drawBitmap(bitmap!!, null, bounds, paint)
        }
    }

    @Deprecated("Deprecated in Java", ReplaceWith("0"))
    override fun getOpacity(): Int {
        return PixelFormat.TRANSPARENT
    }

    override fun setAlpha(alpha: Int) {
        throw UnsupportedOperationException("Alpha is not supported by this drawable.")
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        throw UnsupportedOperationException("ColorFilter is not supported by this drawable.")
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        val height = bounds.height()
        val width = bounds.width()
        numRectanglesHorizontal = ceil((width / rectangleSize).toDouble()).toInt()
        numRectanglesVertical = ceil((height / rectangleSize).toDouble()).toInt()
        generatePatternBitmap()
    }

    /**
     * This will generate a bitmap with the pattern as big as the rectangle we were allow to draw on.
     * We do this to chache the bitmap so we don't need to recreate it each time draw() is called since it takes a few
     * milliseconds
     */
    private fun generatePatternBitmap() {
        if (bounds.width() <= 0 || bounds.height() <= 0) {
            return
        }

        bitmap = Bitmap.createBitmap(bounds.width(), bounds.height(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap!!)

        val r = Rect()
        var verticalStartWhite = true
        for (i in 0..numRectanglesVertical) {
            var isWhite = verticalStartWhite
            for (j in 0..numRectanglesHorizontal) {
                r.top = i * rectangleSize
                r.left = j * rectangleSize
                r.bottom = r.top + rectangleSize
                r.right = r.left + rectangleSize
                canvas.drawRect(r, if (isWhite) paintWhite else paintGray)
                isWhite = !isWhite
            }
            verticalStartWhite = !verticalStartWhite
        }
    }
}