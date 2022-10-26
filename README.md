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
5. RefreshLayout + PullToRefresh + VerticalRefreshableLayout
6. FlowLayout
7. GoodTextField + PasswordTextField
8. MenuFloatingActionButton
9. ChainScrollableComponent + ScrollableAppBar + SwipeToDismiss

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

version
= [![](https://jitpack.io/v/ltttttttttttt/ComposeViews.svg)](https://jitpack.io/#ltttttttttttt/ComposeViews)

```kotlin
dependencies {
    ...
    implementation("com.github.ltttttttttttt:ComposeViews:$version")//this, such as 1.2.2
}
```

## ComposePager

<div align=center><img src="md_resource/compose_pager.gif" width=25%></div>

```kotlin
/**
 * Equivalent to the ViewPager in android
 * @param pageCount Sum page count
 * @param modifier
 * @param composePagerState ComposePager's state
 * @param orientation Scroll orientation
 * @param userEnable Whether the user can scroll
 * @param pageCache The number of pagers cached on the left and right sides
 * @param scrollableInteractionSource Scroll state monitor
 * @param content Content of compose
 */
@Composable
fun ComposePager()
```

## Banner

```kotlin
/**
 * [ComposePager] that can auto scroll
 * @param pageCount Sum page count
 * @param modifier
 * @param bannerState Banner's state
 * @param orientation Scroll orientation
 * @param userEnable Whether the user can scroll
 * @param autoScroll Whether to scroll automatically
 * @param autoScrollTime Auto scroll interval
 * @param content Content of compose
 */
@Composable
fun Banner()
```

## PagerIndicator

<div align=center><img src="md_resource/image_banner.gif" width=50%></div>

<div align=center><img src="md_resource/text_pager_indicator.gif" width=50%></div>

```kotlin
/**
 * Indicator for pager
 * @param size Number of indicator
 * @param offsetPercentWithSelect The offset percentage of the selected indicator
 * @param selectIndex The index of selected indicator
 * @param indicatorItem The indicator
 * @param selectIndicatorItem The selected indicator
 * @param modifier
 * @param margin Spacing between indicators
 * @param orientation Orientation of indicators
 * @param userCanScroll Whether the user can scroll
 */
@Composable
fun PagerIndicator()

/**
 * Text indicator for pager
 * @param texts The text list
 * @param offsetPercentWithSelect The offset percentage of the selected indicator
 * @param selectIndex The index of selected indicator
 * @param fontSize Font size of the text indicator
 * @param selectFontSize Font size of the selected text indicator
 * @param textColor Font color of the text indicator
 * @param selectTextColor Font color of the selected text indicator
 * @param selectIndicatorColor Color of the indicator
 * @param onIndicatorClick Click event of the text indicator
 * @param modifier
 * @param margin Spacing between the text indicators
 * @param userCanScroll Whether the user can scroll
 */
@Composable
fun TextPagerIndicator()
```

## ImageBanner

```kotlin
/**
 * [Banner] showing images
 * @param imageSize Number of images
 * @param imageContent Content of the images
 * @param indicatorItem The indicator, if null, do not display indicator
 * @param selectIndicatorItem The indicator, if null, do not display indicator
 * @param modifier
 * @param bannerState Banner's state
 * @param orientation Orientation of indicators
 * @param autoScroll Whether to scroll automatically
 * @param autoScrollTime Auto scroll interval
 */
@Composable
fun ImageBanner()
```

## RefreshLayout

<div align=center><img src="md_resource/refresh_layout.gif" width=30%></div>

```kotlin
/**
 * The refreshed container can be dragged in any direction
 * @param refreshContent Refreshed content area
 * @param refreshLayoutState State of the [RefreshLayout]
 * @param modifier
 * @param refreshContentThreshold Refresh threshold for layout dragging
 * @param composePosition Set where the refreshed layout is located
 * @param contentIsMove Whether the content component moves with it on refresh
 * @param dragEfficiency The 'efficiency' of dragging
 * @param isSupportCanNotScrollCompose Whether to support non-scrollable components
 * @param userEnable Whether the user can drag
 * @param content Content of compose
 */
@Composable
fun RefreshLayout()

/**
 * Pull down to refresh
 * @param refreshLayoutState State of the [RefreshLayout]
 * @param modifier
 * @param refreshContent Refreshed content area
 * @param content Content of compose
 */
@Composable
fun PullToRefresh()

/**
 * Pull down and up refresh components
 * @param topRefreshLayoutState State of the top of the [RefreshLayout]
 * @param bottomRefreshLayoutState State of the bottom of the [RefreshLayout]
 * @param modifier
 * @param topRefreshContent Refreshed content area of top
 * @param bottomIsLoadFinish Bottom is it loaded
 * @param bottomRefreshContent Refreshed content area of bottom
 * @param content Content of compose
 */
@Composable
fun VerticalRefreshableLayout()
```

## FlowLayout

<div align=center><img src="md_resource/flow_layout.png" width=40%></div>

```kotlin
/**
 * Linear layout with word wrapping
 * @param modifier
 * @param orientation Direction of arrangement
 * @param horizontalAlignment Alignment of horizontal
 * @param verticalAlignment Alignment of vertical
 * @param horizontalMargin Margin of horizontal
 * @param verticalMargin Margin of vertical
 * @param maxLines How many lines can be placed
 * @param content Content of compose
 */
@Composable
fun FlowLayout()
```

## GoodTextField and PasswordTextField

<div align=center><img src="md_resource/text_field.png" width=30%></div>

```kotlin
/**
 * More convenient and easy to use the [TextField]
 * @param value Text of the [TextField]
 * @param onValueChange Text change of the [TextField]
 * @param modifier
 * @param hint Content of the [TextField] with if value is Empty
 * @param maxLines How many lines of text can be displayed
 * @param fontSize Font size of text and hint
 * @param fontColor Color of text
 * @param maxLength How many texts can be displayed at most
 * @param contentAlignment Text and hint to the way
 * @param leading Components displayed on the start
 * @param trailing Components displayed on the end
 * @param background The background
 * @param horizontalPadding Horizontal inner spacing
 * @param enabled Is it possible to enter
 * @param readOnly Read-only
 * @param textStyle The [TextStyle]
 * @param keyboardOptions Reference the [BasicTextField]
 * @param keyboardActions Reference the [BasicTextField]
 * @param visualTransformation Reference the [BasicTextField]
 * @param onTextLayout Reference the [BasicTextField]
 * @param interactionSource Reference the [BasicTextField]
 * @param cursorBrush Reference the [BasicTextField]
 */
@Composable
fun GoodTextField()

/**
 * More convenient and easy to use the [TextField], for entering passwords
 * Api is almost the same as the [GoodTextField]
 */
@Composable
fun PasswordTextField()
```

## MenuFloatingActionButton

<div align=center><img src="md_resource/fab.gif" width=20%></div>

```kotlin
/**
 * Floating action button
 * @param icon Menu icon
 * @param label Menu text
 * @param srcIconColor Icon color
 * @param labelTextColor Label text color
 * @param labelBackgroundColor Background color of label text
 * @param fabBackgroundColor Background color of floating action button
 */
@Composable
fun MenuFloatingActionButton()
```

## ChainScrollableComponent

<div align=center><img src="md_resource/scrollable_app_bar.gif" width=40%></div>

<div align=center><img src="md_resource/swipe_to_dismiss.gif" width=20%></div>

```kotlin
/**
 * 链式(联动)滚动组件
 * @param minScrollPosition 最小滚动位置(距离指定方向的顶点)
 * @param maxScrollPosition 最大滚动位置(距离指定方向的顶点)
 * @param chainContent 链式(联动)滚动的compose组件,scrollOffset: 滚动位置(位于最小和最大之间)
 * @param modifier 修饰
 * @param onScrollStop 停止滚动时回调
 * @param composePosition 设置bar布局所在的位置,并且间接指定了滑动方向
 * @param chainMode 联动方式
 * @param content compose内容区域,需要内容是在相应方向可滚动的,并且需要自行给内容设置相应方向的PaddingValues或padding
 */
@Composable
fun ChainScrollableComponent()

/**
 * Scalable top navigation bar
 * @param title Title of top bar
 * @param background Background of top bar
 * @param modifier
 * @param onScrollStop Callback of scroll stop event
 * @param minScrollPosition Minimum scroll position
 * @param maxScrollPosition Maximum scroll position
 * @param navigationIcon Icon of top bar
 * @param composePosition Set the position of the top bar layout
 * @param chainMode Chain mode
 * @param content Content of compose
 */
@Composable
fun ScrollableAppBar()

/**
 * Swipe to delete controls
 * @param minScrollPosition Minimum scroll position
 * @param maxScrollPosition Maximum scroll position
 * @param backgroundContent Content of background
 * @param modifier
 * @param contentIsMove Does content follow
 * @param content Content of compose
 */
@Composable
fun SwipeToDismiss()
```

<h6>Finally, thank <a href="https://www.jetbrains.com/?from=ltviews" target="_blank">JetBrains</a>
for its support to this project<h6>
