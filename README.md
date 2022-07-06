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
3. BannerIndicator
4. ImageBanner
5. RefreshLayout
6. FlowLayout

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
fun ComposePager(
    pageCount: Int,
    modifier: Modifier = Modifier,
    composePagerState: ComposePagerState = rememberComposePagerState(),
    orientation: Orientation = Orientation.Horizontal,
    userEnable: Boolean = true,
    content: @Composable ComposePagerScope.() -> Unit
) {
}
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
fun Banner(
    pageCount: Int,
    modifier: Modifier = Modifier,
    bannerState: BannerState = rememberBannerState(),
    orientation: Orientation = Orientation.Horizontal,
    userEnable: Boolean = true,
    autoScroll: Boolean = true,
    autoScrollTime: Long = 3000,
    content: @Composable BannerScope.() -> Unit
) {
}
```

## BannerIndicator

```kotlin

```

## ImageBanner

```kotlin

```

## RefreshLayout

```kotlin

```

## FlowLayout

```kotlin
/**
 * 可以自动换行的线性布局
 * [modifier]修饰
 * [orientation]排列的方向,[Orientation.Horizontal]时会先横向排列,一排放不下会换到下一行继续横向排列
 * [horizontalAlignment]子级在横向上的位置
 * [verticalArrangement]子级在竖向上的位置
 * [horizontalMargin]子级在横向上的间距
 * [verticalMargin]子级在竖向上的间距
 * [maxLines]最多能放多少行(或列)
 * [content]compose内容区域
 */
@Composable
fun FlowLayout(
    modifier: Modifier = Modifier,
    orientation: Orientation = Orientation.Horizontal,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalMargin: Dp = 0.dp,
    verticalMargin: Dp = 0.dp,
    maxLines: Int = Int.MAX_VALUE,
    content: @Composable () -> Unit
) {
}
```