# ColorPickerX

### A simple Android color picker library
[![GitHub release](https://img.shields.io/github/release/otakuhqz/ColorPickerX.svg?style=for-the-badge)](https://github.com/otakuhqz/ColorPickerX/releases)
[![License](https://img.shields.io/badge/License-GPL%20v3-blue.svg?style=for-the-badge)](https://github.com/otakuhqz/colorpickerx/blob/main/LICENSE)
[![Donate](https://img.shields.io/badge/Paypal-Donate-red?style=for-the-badge&logo=paypal)](https://www.paypal.com/paypalme/omegalauncher)

<table>
    <tr>
        <td><img src="https://raw.github.com/otakuhqz/colorpicker/master/screen1.jpg" width="256" /></td>
        <td><img src="https://raw.github.com/otakuhqz/colorpicker/master/screen2.jpg" width="256" /></td>
    </tr>
    <tr>
        <td><img src="https://raw.github.com/otakuhqz/colorpicker/master/screen3.jpg" width="256" /></td>
        <td><img src="https://raw.github.com/otakuhqz/colorpicker/master/screen4.jpg" width="256" /></td>
    </tr>
</table>

## Download ##

Download the [latest JAR](https://github.com/otakuhqz/colorpicker/packages/670377) or grab via Gradle:

```groovy
implementation 'com.github.otakuhqz:colorpickerx:1.1.10'
```

## How to use ##

  Example 1 : 
```java
ColorPicker colorPicker = new ColorPicker(activity);
colorPicker.show();
colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
	@Override
	public void onChooseColor(int position,int color) {
	    // put code
	}

	@Override
	public void onCancel(){
	// put code
	}
});
```  
  Example 2 : 
```java
final ColorPicker colorPicker = new ColorPicker(SampleActivity.this);
colorPicker.setFastChooser(new ColorPicker.OnFastChooseColorListener() {
	@Override
	public void setOnFastChooseColorListener(int position, int color) {
	  // put code
	}

	@Override
	public void onCancel(){
	// put code
	}
})
.setDefaultColor(Color.parseColor("#f84c44"))
.setColumns(5)
.show();
```
  Example 3 : 
```java
final ColorPicker colorPicker = new ColorPicker(SampleActivity.this);
colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
	@Override
	public void onChooseColor(int position,int color) {
	// put code
	}

	@Override
	public void onCancel(){
	// put code
	}
})
.addListenerButton("newButton", new ColorPicker.OnButtonListener() {
	@Override
	public void onClick(View v, int position, int color) {
	    // put code
	}
})
.disableDefaultButtons(true)
.setDefaultColor(Color.parseColor("#f84c44"))
.setColumns(5)
.setDialogFullHeight()
.show();
```

## What you can do ##

Choose your own colors

```java
setColors(int resId); // using an array resource
setColors(ArrayList<String> colorsHexList); // using a list of hex colors
setColors(int... colorsList); // use a list of colors Color.RED,Color.Black etc
setDefaultColorButton(int color); // set the colorButton to check by default
```

Define Listeners

```java
setOnFastChooseColorListener(OnFastChooseColorListener listener); // renamed in version 1.1.0
setOnChooseColorListener(OnChooseColorListener listener);
```

Add custom buttons

```java
addListenerButton(String text, Button button, OnButtonListener listener); // custom button
addListenerButton(String text, final OnButtonListener listener); // it will generate a button with default style
```

General methods you can use:

```java
setDialogFullHeight(); // bigger height
dismissDialog(); // dismiss dialog slowly
setColumns(int c); // set columns number
setTitle(String title); // set the title of the dialog
setTitlePadding(int left, int top, int right, int bottom);
disableDefaultButtons(boolean disableDefaultButtons); // use if you want to implement your own buttons

getDialogBaseLayout(); // returns the RelativeLayout used as base for the dialog
getDialogViewLayout(); // returns the view inflated into the dialog
getDialog(); // returns the dialog
getPositiveButton(); // returns the positive button defined by default
getNegativeButton(); // returns the negative button defined by default
setDismissOnButtonListenerClick(boolean dismiss); // renamed in version 1.1.0
```

ColorButtons changes you can do:

```java
setColorButtonTickColor(int color);  // renamed in version 1.1.0
setColorButtonDrawable(int drawable);
setColorButtonSize(int width, int height);
setColorButtonMargin(int left, int top, int right, int bottom);
setRoundColorButton(boolean roundButton);
```

## Credits
The original project was developed by Petrov Kristiyan
[https://github.com/kristiyanP/colorpicker](https://github.com/kristiyanP/colorpicker)

The custom picker selector was developed by Jared Rummler 
[https://github.com/jaredrummler](https://github.com/jaredrummler)

## Additional Credits ##
for the Material Dialog library for button design specs and implementation
  [https://github.com/drakeet/MaterialDialog](https://github.com/drakeet/MaterialDialog)