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

package com.shlabs.colorpickerx.views

class ColorPal(parseColor: Int, mCheck: Boolean) {
    private var color = parseColor
    private var check = mCheck

    override fun equals(other: Any?): Boolean {
        return other is ColorPal && other.color == color
    }

    fun getColor(): Int {
        return color
    }

    fun setColor(color: Int) {
        this.color = color
    }

    fun isCheck(): Boolean {
        return check
    }

    fun setCheck(check: Boolean) {
        this.check = check
    }

    override fun hashCode(): Int {
        var result = color
        result = 31 * result + check.hashCode()
        return result
    }
}