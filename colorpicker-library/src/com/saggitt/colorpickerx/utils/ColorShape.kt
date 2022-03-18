package com.saggitt.colorpickerx.utils

import androidx.annotation.IntDef

@IntDef(ColorShape.SQUARE, ColorShape.CIRCLE)
annotation class ColorShape {
    companion object {
        const val SQUARE = 0
        const val CIRCLE = 1
    }
}
