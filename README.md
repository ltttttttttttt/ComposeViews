<h1 align="center">ComposeViews</h1>

<p align="center">Jetpack(jb) Compose Views, in to Android,Web,Desktop,Ios...</p>

<p align="center">
<img src="https://img.shields.io/badge/license-Apache%202-blue.svg?maxAge=2592000">
<img src="https://jitpack.io/v/ltttttttttttt/ComposeViews.svg"/>
</p>

<div align="center">us English | <a href="https://github.com/ltttttttttttt/ComposeViews/blob/main/README_CN.md">cn 简体中文</a></div>

## Views:

1. ComposePager
2. Banner
3. PagerIndicator
4. ImageBanner
5. RefreshLayout
6. FlowLayout
7. GoodTextField and PasswordTextField
8. MenuFloatingActionButton

## Add to your project

Step 1.Root dir, build.gradle.kts add:

```kotlin
buildscript {
    repositories {
        maven("https://jitpack.io")//this
        ...
    }
}

allprojects {
    repositories {
        maven("https://jitpack.io")//this
        ...
    }
}
```

Step 2.Your app dir, build.gradle.kts add:

version = [![](https://jitpack.io/v/ltttttttttttt/ComposeViews.svg)](https://jitpack.io/#ltttttttttttt/ComposeViews)

```kotlin
dependencies {
    ...
    implementation("com.github.ltttttttttttt:ComposeViews:$version")//this
}
```

## ComposePager

<div align=center><img src="md_resource/compose_pager.gif" width=30%></div>

```kotlin
/**
 * 类似于xml中的ViewPager
 * [pageCount]一共有多少页
 * [modifier]修饰
 * [composePagerState]ComposePager的状态
 * [orientation]滑动的方向
 * [userEnable]用户是否可以滑动,等于false时用户滑动无反应,但代码可以执行翻页
 * [content]compose内容区域
 */
@Composable
fun ComposePager()
```

## Banner

```kotlin
/**
 * 可以自动循环轮播的ComposePager
 * [pageCount]一共有多少页
 * [modifier]修饰
 * [bannerState]Banner的状态
 * [orientation]滑动的方向
 * [userEnable]用户是否可以滑动,等于false时用户滑动无反应,但代码可以执行翻页
 * [autoScroll]是否自动滚动
 * [autoScrollTime]自动滚动间隔时间
 * [content]compose内容区域
 */
@Composable
fun Banner()
```

## PagerIndicator

```kotlin
/**
 * 适用于Pager的指示器
 * [size]指示器数量
 * [offsetPercentWithSelect]选中的指示器的偏移百分比
 * [selectIndex]选中的索引
 * [indicatorItem]未被选中的指示器
 * [selectIndicatorItem]被选中的指示器
 * [modifier]修饰
 * [margin]指示器之间的间距
 * [orientation]指示器排列方向
 */
@Composable
fun PagerIndicator()
```

## ImageBanner

```kotlin

```

## RefreshLayout

```kotlin

```

## FlowLayout

<div align=center><img src="md_resource/flow_layout.png" width=40%></div>

```kotlin
/**
 * 可以自动换行的线性布局
 * [modifier]修饰
 * [orientation]排列的方向,[Orientation.Horizontal]时会先横向排列,一排放不下会换到下一行继续横向排列
 * [horizontalAlignment]子级在横向上的位置
 * [verticalAlignment]子级在竖向上的位置
 * [horizontalMargin]子级与子级在横向上的间距
 * [verticalMargin]子级与子级在竖向上的间距
 * [maxLines]最多能放多少行(或列)
 * [content]compose内容区域
 */
@Composable
fun FlowLayout()
```

## GoodTextField and PasswordTextField

<div align=center><img src="md_resource/text_field.png" width=30%></div>

```kotlin
/**
 * 更方便易用的TextField(文本输入框)
 * [value]输入框中的文字
 * [onValueChange]输入框中文字的变化回调
 * [modifier]修饰
 * [hint]输入框没有文字时展示的内容
 * [maxLines]最多能展示多少行文字
 * [fontSize]text和hint的字体大小
 * [fontColor]text的字体颜色
 * [maxLength]最多能展示多少个文字,ps:由于会截断文字,会导致截断时重置键盘状态(TextField特性)
 * [contentAlignment]text和hint对其方式
 * [leading]展示在左边的组件
 * [trailing]展示在右边的组件
 * [background]背景
 * [horizontalPadding]横向的内间距
 * [enabled]是否可输入,false无法输入和复制
 * [readOnly]是否可输入,true无法输入,但可复制,获取焦点,移动光标
 * [textStyle]字体样式
 * [keyboardOptions]键盘配置
 * [keyboardActions]键盘回调
 * [visualTransformation]文本展示的转换
 * [onTextLayout]计算新文本布局时执行的回调
 * [interactionSource]状态属性
 * [cursorBrush]光标绘制
 */
@Composable
fun GoodTextField()

/**
 * 更方便易用的TextField,适用于输入密码的情况
 * [value]输入框中的文字
 * [onValueChange]输入框中文字的变化回调
 * [passwordIsShow]密码是否可见,false为密文状态
 * [onPasswordIsShowChange]密码是否可见状态变化的回调
 * [modifier]修饰
 * [hint]输入框没有文字时展示的内容
 * [maxLines]最多能展示多少行文字
 * [fontSize]text和hint的字体大小
 * [fontColor]text的字体颜色
 * [maxLength]最多能展示多少个文字,ps:由于会截断文字,会导致截断时重置键盘状态(TextField特性)
 * [contentAlignment]text和hint对其方式
 * [leading]展示在左边的组件
 * [trailing]展示在右边的组件,默认是可点击的眼睛图标,用于切换密码是否可见
 * [background]背景
 * [horizontalPadding]横向的内间距
 * [enabled]是否可输入,false无法输入和复制
 * [readOnly]是否可输入,true无法输入,但可复制,获取焦点,移动光标
 * [textStyle]字体样式
 * [keyboardOptions]键盘配置
 * [keyboardActions]键盘回调
 * [passwordChar]密码不可见时展示的字符
 * [visualTransformation]文本展示的转换
 * [onTextLayout]计算新文本布局时执行的回调
 * [interactionSource]状态属性
 * [cursorBrush]光标绘制
 */
@Composable
fun PasswordTextField()
```

## MenuFloatingActionButton

<div align=center><img src="md_resource/fab.gif" width=20%></div>

```kotlin
/**
 * 带菜单的Fab
 *
 * [srcIcon]按钮的图标
 * [items]菜单项
 * [modifier]修饰
 * [srcIconColor]按钮图标的颜色
 * [fabBackgroundColor]按钮区域背景色
 * [showLabels]是否展示菜单项的提示文本
 * [onFabItemClicked]菜单项点击事件
 */
@Composable
fun MenuFloatingActionButton()
```

<h6>Finally, thank <a href="https://www.jetbrains.com/?from=ltviews" target="_blank">JetBrains</a> for its support to this project<h6>