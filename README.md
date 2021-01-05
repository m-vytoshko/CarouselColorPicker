# CarouselColorPicker

#### Latest version
[![](https://jitpack.io/v/m-vytoshko/CarouselColorPicker.svg)](https://jitpack.io/#m-vytoshko/CarouselColorPicker)

<a href="https://github.com/sssbohdan/CircularColorPicker">Also available for iOS: </a>


<img src="https://github.com/m-vytoshko/CarouselColorPicker/blob/main/img/showcase.gif?raw=true" width="400" height="711">


#### Implementation 
Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
	...
        maven { url 'https://jitpack.io' }
    }
}
```
Add the dependency
```
implementation("com.github.m-vytoshko:CarouselColorPicker:latestVersion")
```

### Customization
##### To change colors:
Implement ``` IPalette```
##### To draw different selection cursor:
Implement ``` IndicatorPainter```
##### Set number of shadows:
```

 <com.mv.colorpicker.ColorPickerView
        ...
        app:shadesCount="5" />
```
##### Set shadow darkening step:
```

 <com.mv.colorpicker.ColorPickerView
        ...
        app:shadeRatio="0.05" />
```