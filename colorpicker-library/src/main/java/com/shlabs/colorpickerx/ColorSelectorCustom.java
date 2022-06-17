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

package com.shlabs.colorpickerx;

import static com.shlabs.colorpickerx.utils.ColorUtils.dip2px;
import static com.shlabs.colorpickerx.utils.ColorUtils.getDimensionDp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.shlabs.colorpickerx.utils.ColorUtils;
import com.shlabs.colorpickerx.utils.CustomDialog;
import com.shlabs.colorpickerx.views.CustomPickerSelector;
import com.shlabs.colorpickerx.views.PanelView;

import java.lang.ref.WeakReference;

public class ColorSelectorCustom implements CustomPickerSelector.OnColorChangedListener, TextWatcher {
    private OnChooseColorListener onChooseColorListener;
    private OnFastChooseColorListener onFastChooseColorListener;

    private final RelativeLayout buttons_layout;
    private final View dialogViewLayout;
    private final RelativeLayout colorpicker_base;
    private int defaultColor;
    private final int marginColorButtonLeft;
    private final int marginColorButtonRight;
    private final int marginColorButtonTop;
    private final int marginColorButtonBottom;
    private int paddingTitleLeft, paddingTitleRight, paddingTitleBottom, paddingTitleTop;
    private final Button positiveButton;
    private final Button neutralButton;
    private final Context mContext;
    private boolean dismiss;
    private boolean fullHeight;
    private boolean fastChooser;
    private boolean disableDefaultButtons;
    private String title;
    private final String neutralText;
    private final String positiveText;
    private int color;
    private final EditText hexEditText;
    private final PanelView newColorPanel;
    private final PanelView oldColorPanel;
    private final CustomPickerSelector colorPicker;
    private boolean fromEditText;
    boolean showAlphaSlider;
    private WeakReference<CustomDialog> mDialog;

    public ColorSelectorCustom(Context context) {
        dialogViewLayout = LayoutInflater.from(context).inflate(R.layout.color_selector_custom, null, false);
        colorpicker_base = dialogViewLayout.findViewById(R.id.colorpicker_base);
        buttons_layout = dialogViewLayout.findViewById(R.id.buttons_layout);
        positiveButton = dialogViewLayout.findViewById(R.id.positive);
        neutralButton = dialogViewLayout.findViewById(R.id.neutral_button);
        mContext = context;
        this.dismiss = true;
        this.marginColorButtonLeft = this.marginColorButtonTop = this.marginColorButtonRight = this.marginColorButtonBottom = 5;
        this.title = context.getString(R.string.colorpicker_dialog_title);
        this.neutralText = context.getString(R.string.color_presets);
        this.positiveText = context.getString(android.R.string.ok);
        this.defaultColor = 0;

        hexEditText = dialogViewLayout.findViewById(R.id.cpx_hex);
        oldColorPanel = dialogViewLayout.findViewById(R.id.cpx_color_panel_current);
        newColorPanel = dialogViewLayout.findViewById(R.id.cpx_color_panel_new);
        colorPicker = dialogViewLayout.findViewById(R.id.cpx_color_picker_selector);
        colorPicker.setAlphaSliderVisible(showAlphaSlider);
        color = Color.BLUE;

        oldColorPanel.setColor(color);
        colorPicker.setColor(color, true);
        newColorPanel.setColor(color);
        setHex(color);
        if (!showAlphaSlider) {
            hexEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        }
        newColorPanel.setOnClickListener(v -> {
            if (newColorPanel.getColor() == color) {
                onColorSelected(color);
                //dismiss();
            }
        });
        colorPicker.setOnColorChangedListener(this);
        hexEditText.addTextChangedListener(this);

        hexEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(hexEditText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (hexEditText.isFocused()) {
            int color = ColorUtils.parseColorString(s.toString());
            if (color != colorPicker.getColor()) {
                fromEditText = true;
                colorPicker.setColor(color, true);
            }
        }
    }

    @Override
    public void onColorChanged(int newColor) {
        color = newColor;
        if (newColorPanel != null) {
            newColorPanel.setColor(newColor);
        }
        if (!fromEditText && hexEditText != null) {
            setHex(newColor);
            if (hexEditText.hasFocus()) {
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(hexEditText.getWindowToken(), 0);
                hexEditText.clearFocus();
            }
        }
        fromEditText = false;
    }

    private void onColorSelected(int color) {

    }

    private void setHex(int color) {
        if (showAlphaSlider) {
            hexEditText.setText(String.format("%08X", (color)));
        } else {
            hexEditText.setText(String.format("%06X", (0xFFFFFF & color)));
        }
    }

    public ColorSelectorCustom showAlpha(boolean showAlpha) {
        showAlphaSlider = showAlpha;
        colorPicker.setAlphaSliderVisible(showAlphaSlider);

        return this;
    }

    public ColorSelectorCustom setTitle(String title) {
        this.title = title;
        return this;
    }


    public ColorSelectorCustom setDefaultColorButton(int color) {
        oldColorPanel.setColor(color);
        newColorPanel.setColor(color);
        colorPicker.setColor(color);
        this.defaultColor = color;
        return this;
    }

    public ColorSelectorCustom setOnFastChooseColorListener(OnFastChooseColorListener listener) {
        this.fastChooser = true;
        buttons_layout.setVisibility(View.GONE);
        this.onFastChooseColorListener = listener;
        dismissDialog();
        return this;
    }


    public ColorSelectorCustom setOnChooseColorListener(OnChooseColorListener listener) {
        onChooseColorListener = listener;
        return this;
    }

    public ColorSelectorCustom addListenerButton(String text, Button button, final ColorPickerTab.OnButtonListener listener) {
        button.setText(text);
        if (button.getParent() != null)
            buttons_layout.removeView(button);
        buttons_layout.addView(button);
        return this;
    }

    public ColorSelectorCustom addListenerButton(String text, final ColorPickerTab.OnButtonListener listener) {
        if (mContext == null)
            return this;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        params.setMargins(dip2px(10, mContext), 0, 0, 0);
        Button button = new Button(mContext);
        button.setMinWidth(getDimensionDp(R.dimen.action_button_min_width, mContext));
        button.setMinimumWidth(getDimensionDp(R.dimen.action_button_min_width, mContext));
        button.setPadding(
                getDimensionDp(R.dimen.action_button_padding_horizontal, mContext) + dip2px(5, mContext), 0,
                getDimensionDp(R.dimen.action_button_padding_horizontal, mContext) + dip2px(5, mContext), 0);
        button.setBackgroundResource(R.drawable.button);
        button.setTextSize(getDimensionDp(R.dimen.action_button_text_size, mContext));
        button.setTextColor(ContextCompat.getColor(mContext, R.color.black_de));

        button.setText(text);
        if (button.getParent() != null)
            buttons_layout.removeView(button);

        buttons_layout.addView(button);
        button.setLayoutParams(params);
        return this;
    }

    public ColorSelectorCustom setTitlePadding(int left, int top, int right, int bottom) {
        paddingTitleLeft = left;
        paddingTitleRight = right;
        paddingTitleTop = top;
        paddingTitleBottom = bottom;
        return this;
    }

    public void show() {
        if (mContext == null)
            return;
        AppCompatTextView titleView = dialogViewLayout.findViewById(R.id.title);
        if (title != null) {
            titleView.setText(title);
            titleView.setPadding(
                    dip2px(paddingTitleLeft, mContext), dip2px(paddingTitleTop, mContext),
                    dip2px(paddingTitleRight, mContext), dip2px(paddingTitleBottom, mContext));
        }
        mDialog = new WeakReference<>(new CustomDialog(mContext, dialogViewLayout));

        if (fullHeight) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            //lp.addRule(RelativeLayout.BELOW, titleView.getId());
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            colorpicker_base.setLayoutParams(lp);
        }

        if (defaultColor != 0) {
            oldColorPanel.setColor(defaultColor);
            colorPicker.setColor(defaultColor);
        }

        if (disableDefaultButtons) {
            positiveButton.setVisibility(View.GONE);
        }

        positiveButton.setText(positiveText);
        neutralButton.setText(neutralText);
        positiveButton.setOnClickListener(v -> {
            if (onChooseColorListener != null && !fastChooser)
                onChooseColorListener.onChooseColor(-1, newColorPanel.getColor());

            if (dismiss) {
                dismissDialog();
                if (onFastChooseColorListener != null) {
                    onFastChooseColorListener.onCancel();
                }
            }
        });


        neutralButton.setOnClickListener(v -> {
            int tempColor = 0;
            if (onChooseColorListener != null && !fastChooser) {
                onChooseColorListener.onChooseColor(-1, newColorPanel.getColor());
                tempColor = newColorPanel.getColor();
            }
            if (dismiss)
                dismissDialog();
            if (onChooseColorListener != null)
                onChooseColorListener.onCancel();
            ColorSelectorPresets colorPicker = new ColorSelectorPresets(v.getContext());
            colorPicker.setTitle(title)
                    .setColumns(4)
                    .setColorButtonSize(48, 48)
                    .setRoundColorButton(true)
                    .setTitle(title)
                    .setOnChooseColorListener(onChooseColorListener)
                    .setDefaultColorButton(tempColor)
                    .show();

        });


        if (mDialog == null) {
            return;
        }

        Dialog dialog = mDialog.get();

        if (dialog != null) {
            dialog.show();
            //Keep mDialog open when rotate
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(lp);
        }

    }

    public ColorSelectorCustom setDismissOnButtonListenerClick(boolean dismiss) {
        this.dismiss = dismiss;
        return this;
    }

    public ColorSelectorCustom setDialogFullHeight() {
        this.fullHeight = true;
        return this;
    }

    public @Nullable CustomDialog getDialog() {
        if (mDialog == null)
            return null;
        return mDialog.get();
    }

    public View getDialogViewLayout() {
        return dialogViewLayout;
    }

    public RelativeLayout getDialogBaseLayout() {
        return colorpicker_base;
    }

    public Button getPositiveButton() {
        return positiveButton;
    }

    public void dismissDialog() {
        if (mDialog == null)
            return;

        Dialog dialog = mDialog.get();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private ColorSelectorCustom setMargin(int left, int top, int right, int bottom) {
        return this;
    }
}
