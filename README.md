# AcsUtils
> Utilities for Android Developers

[![](https://jitpack.io/v/acslmv/AcsUtils.svg)](https://jitpack.io/#acslmv/AcsUtils)

## Widgets

Name | Status
------------    | -------------
AcsBox          | ----
AcsButton       | -----
AcsLock         | ----
AcsToast        | --

[![](https://media.giphy.com/media/Ym9YAIkYTPvhe/giphy.gif)](#)

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
    ...
    compile 'com.github.acslmv:AcsUtils:1.3'
}
```


## AcsButton
- XML Atributos

Name                    | Format    | Description
---                     | ---       | ---
ab_centered             | boolean   | Centrar contenido
ab_bgColor              | color     | -
ab_bgColorFocus         | color     | -
ab_bgColorDisabled      | color     | Color de fondo inabilitado (por defecto aplica transparencia)
ab_text                 | string    | -
ab_textSize             | dimension | -
ab_textBold             | boolean   | Texto en negrita
ab_textAllCaps          | boolean   | Texto mayuscula
ab_textColor            | color     | -
ab_textSingleLine       | boolean   | Texto en una sola linea (aplica 3 puntos al final)
ab_radius               | dimension | Border redondeado
ab_borderColor          | color     | -
ab_borderColorDisabled  | color     | -
ab_borderWidth          | dimension | -
ab_loadingSize          | dimension | -
ab_loadingColor         | color     | -
ab_icon                 | reference | -
ab_iconSize             | dimension | -
ab_iconColor            | color     | -
ab_iconMarginLeft       | dimension | -
ab_iconMarginRight      | dimension | -
ab_iconMarginTop        | dimension | -
ab_iconMarginBottom     | dimension | -
ab_iconTop              | boolean   | -

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
 
- JAVA

```java
...
button.setLoading();
button.hideLoading();
```




## Author
- [Alvaro C. S.](http://www.alvarocs.com/)



