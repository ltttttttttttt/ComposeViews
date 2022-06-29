<h1 align="center">ComposeViews</h1>

<p align="center">适用于Android,Web,Desktop,Ios的Jetpack Compose View</p>

<p align="center">
<img src="https://img.shields.io/badge/license-Apache%202-blue.svg?maxAge=2592000">
<img src="https://jitpack.io/v/ltttttttttttt/ComposeViews.svg"/>
</p>

<div align="center"><a href="https://github.com/ltttttttttttt/ComposeViews/blob/main/README.md">us English</a> | cn 简体中文</div>

## 目前提供的View

1. ComposePager
2. Banner
3. BannerIndicator
4. ImageBanner
5. RefreshLayout

## 快速入门

Step 1.在项目的根目录的build.gradle.kts内添加:

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

Step 2.在app模块目录内的build.gradle.kts内添加:

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