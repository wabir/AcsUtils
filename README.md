# AcsUtils

[![](https://jitpack.io/v/acslmv/AcsUtils.svg)](https://jitpack.io/#acslmv/AcsUtils)

>Utilidades


## Preview

<img src="https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/screen.gif" width="240px" height="240px"/>

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
    compile 'com.github.acslmv:AcsUtils:1.0'
}
```


## AcsButton
- Xml

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
 
- Java

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







## Acknowledgements
- [SpinKit](https://github.com/tobiasahlin/SpinKit).



