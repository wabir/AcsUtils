# Android-Utils

[![](https://jitpack.io/v/acslmv/android-utils.svg)](https://jitpack.io/#acslmv/android-utils)

>Utilidades


## Preview

<img src="https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/screen.gif" width="240px" height="240px"/>

<img src="https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/screen2.gif" width="200px" height="200px"/>

## Gradle Dependency

  1. Add the JitPack repository to your build file

	```gradle
	allprojects {
				repositories {
					...
					maven { url "https://jitpack.io" }
				}
	}
	```

  2. Add the dependency

 ``` gradle
dependencies {
    compile 'com.github.acslmv:android-utils:1.0'
 }
```


## Usage
- AcsButton

 ```xml
<acs.utils.AcsButton
    xmlns:acs="http://schemas.android.com/apk/res-auto"
    android:id="@+id/login"
    android:layout_width="match_parent"
    android:layout_height="50dp"
    acs:ab_bgColor="#39579B"
    acs:ab_bgColorFocus="#193574"
    acs:ab_text="Iniciar con Facebook"
    acs:ab_textBold="true"
    acs:ab_radius="5dp"
    acs:ab_icon="@drawable/ic_facebook"
    acs:ab_iconSize="20dp"
    acs:ab_iconMarginRight="10dp"/>
```
 
- ProgressBar

 ```java
...
button.setLoading();
button.hideLoading();
```

## Style
> 
 ```xml
Good!
 ```

Style | Preview
------------     |   -------------
RotatingPlane    | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/RotatingPlane.gif' alt='RotatingPlane' width="90px" height="90px"/>
DoubleBounce     | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/DoubleBounce.gif' alt='DoubleBounce' width="90px" height="90px"/>
Wave             | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/Wave.gif' alt='Wave' width="90px" height="90px"/>
WanderingCubes   | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/WanderingCubes.gif' alt='WanderingCubes' width="90px" height="90px"/>
Pulse            | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/Pulse.gif' alt='Pulse' width="90px" height="90px"/>
ChasingDots      | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/ChasingDots.gif' alt='ChasingDots' width="90px" height="90px"/>
ThreeBounce      | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/ThreeBounce.gif' alt='ThreeBounce' width="90px" height="90px"/>
Circle           | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/Circle.gif' alt='Circle' width="90px" height="90px"/>
CubeGrid         | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/CubeGrid.gif' alt='CubeGrid' width="90px" height="90px"/>
FadingCircle     | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/FadingCircle.gif' alt='FadingCircle' width="90px" height="90px"/>
FoldingCube      | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/FoldingCube.gif' alt='FoldingCube' width="90px" height="90px"/>
RotatingCircle   | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/RotatingCircle.gif' alt='RotatingCircle' width="90px" height="90px"/>







## Acknowledgements
- [SpinKit](https://github.com/tobiasahlin/SpinKit).



