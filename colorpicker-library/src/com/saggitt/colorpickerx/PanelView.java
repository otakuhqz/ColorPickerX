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

package com.saggitt.colorpickerx;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

public class PanelView extends View {

    private final static int DEFAULT_BORDER_COLOR = 0xFF6E6E6E;

    private Drawable alphaPattern;
    private Paint borderPaint;
    private Paint colorPaint;
    private Paint alphaPaint;
    private Paint originalPaint;
    private Rect drawingRect;
    private Rect colorRect;
    private RectF centerRect = new RectF();
    private boolean showOldColor;

    /* The width in pixels of the border surrounding the color panel. */
    private int borderWidthPx;
    private int borderColor = DEFAULT_BORDER_COLOR;
    private int color = Color.BLACK;
    private int shape;

    private Context mContext;

    public PanelView(Context context) {
        super(context);
        init(context, null);
    }

    public PanelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PanelView);
        shape = a.getInt(R.styleable.PanelView_cpx_colorShape, ColorShape.SQUARE);
        showOldColor = a.getBoolean(R.styleable.PanelView_cpx_showCurrentColor, false);
        if (showOldColor && shape != ColorShape.CIRCLE) {
            throw new IllegalStateException("Color preview is only available in circle mode");
        }
        borderColor = a.getColor(R.styleable.PanelView_cpx_borderColor, DEFAULT_BORDER_COLOR);
        a.recycle();
        if (borderColor == DEFAULT_BORDER_COLOR) {
            // If no specific border color has been set we take the default secondary text color as border/slider color.
            // Thus it will adopt to theme changes automatically.
            final TypedValue value = new TypedValue();
            TypedArray typedArray =
                    context.obtainStyledAttributes(value.data, new int[]{android.R.attr.textColorSecondary});
            borderColor = typedArray.getColor(0, borderColor);
            typedArray.recycle();
        }
        borderWidthPx = dpToPx(1);
        borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        colorPaint = new Paint();
        colorPaint.setAntiAlias(true);
        if (showOldColor) {
            originalPaint = new Paint();
        }
        if (shape == ColorShape.CIRCLE) {
            Bitmap bitmap = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.cpv_alpha)).getBitmap();
            alphaPaint = new Paint();
            alphaPaint.setAntiAlias(true);
            BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            alphaPaint.setShader(shader);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        borderPaint.setColor(borderColor);
        colorPaint.setColor(color);
        if (shape == ColorShape.SQUARE) {
            if (borderWidthPx > 0) {
                canvas.drawRect(drawingRect, borderPaint);
            }
            if (alphaPattern != null) {
                alphaPattern.draw(canvas);
            }
            canvas.drawRect(colorRect, colorPaint);
        } else if (shape == ColorShape.CIRCLE) {
            final int outerRadius = getMeasuredWidth() / 2;
            if (borderWidthPx > 0) {
                canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, outerRadius, borderPaint);
            }
            if (Color.alpha(color) < 255) {
                canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, outerRadius - borderWidthPx, alphaPaint);
            }
            if (showOldColor) {
                canvas.drawArc(centerRect, 90, 180, true, originalPaint);
                canvas.drawArc(centerRect, 270, 180, true, colorPaint);
            } else {
                canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, outerRadius - borderWidthPx, colorPaint);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (shape == ColorShape.SQUARE) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            setMeasuredDimension(width, height);
        } else if (shape == ColorShape.CIRCLE) {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
            setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (shape == ColorShape.SQUARE || showOldColor) {
            drawingRect = new Rect();
            drawingRect.left = getPaddingLeft();
            drawingRect.right = w - getPaddingRight();
            drawingRect.top = getPaddingTop();
            drawingRect.bottom = h - getPaddingBottom();
            if (showOldColor) {
                setUpCenterRect();
            } else {
                setUpColorRect();
            }
        }
    }

    private void setUpCenterRect() {
        final Rect dRect = drawingRect;
        int left = dRect.left + borderWidthPx;
        int top = dRect.top + borderWidthPx;
        int bottom = dRect.bottom - borderWidthPx;
        int right = dRect.right - borderWidthPx;
        centerRect = new RectF(left, top, right, bottom);
    }

    private void setUpColorRect() {
        final Rect dRect = drawingRect;
        int left = dRect.left + borderWidthPx;
        int top = dRect.top + borderWidthPx;
        int bottom = dRect.bottom - borderWidthPx;
        int right = dRect.right - borderWidthPx;
        colorRect = new Rect(left, top, right, bottom);
        alphaPattern = new AlphaPatternDrawable(dpToPx(4));
        alphaPattern.setBounds(Math.round(colorRect.left), Math.round(colorRect.top), Math.round(colorRect.right),
                Math.round(colorRect.bottom));
    }

    /**
     * Get the color currently show by this view.
     *
     * @return the color value
     */
    public int getColor() {
        return color;
    }

    /**
     * Set the color that should be shown by this view.
     *
     * @param color the color value
     */
    public void setColor(int color) {
        this.color = color;
        invalidate();
    }

    private int dpToPx(float dipValue) {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        float val = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
        int res = (int) (val + 0.5);
        return res == 0 && val > 0 ? 1 : res;
    }
}
