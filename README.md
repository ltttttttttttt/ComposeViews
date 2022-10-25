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
 * 可以自动换行的线性布局
 * @param modifier 修饰
 * @param orientation 排列的方向,[Orientation.Horizontal]时会先横向排列,一排放不下会换到下一行继续横向排列
 * @param horizontalAlignment 子级在横向上的位置
 * @param verticalAlignment 子级在竖向上的位置
 * @param horizontalMargin 子级与子级在横向上的间距
 * @param verticalMargin 子级与子级在竖向上的间距
 * @param maxLines 最多能放多少行(或列)
 * @param content compose内容区域
 */
@Composable
fun FlowLayout()
```

## GoodTextField and PasswordTextField

<div align=center><img src="md_resource/text_field.png" width=30%></div>

```kotlin
/**
 * 更方便易用的TextField(文本输入框)
 * @param value 输入框中的文字
 * @param onValueChange 输入框中文字的变化回调
 * @param modifier 修饰
 * @param hint 输入框没有文字时展示的内容
 * @param maxLines 最多能展示多少行文字
 * @param fontSize text和hint的字体大小
 * @param fontColor text的字体颜色
 * @param maxLength 最多能展示多少个文字,ps:由于会截断文字,会导致截断时重置键盘状态(TextField特性)
 * @param contentAlignment text和hint对其方式
 * @param leading 展示在左边的组件
 * @param trailing 展示在右边的组件
 * @param background 背景
 * @param horizontalPadding 横向的内间距
 * @param enabled 是否可输入,false无法输入和复制
 * @param readOnly 是否可输入,true无法输入,但可复制,获取焦点,移动光标
 * @param textStyle 字体样式
 * @param keyboardOptions 键盘配置
 * @param keyboardActions 键盘回调
 * @param visualTransformation 文本展示的转换
 * @param onTextLayout 计算新文本布局时执行的回调
 * @param interactionSource 状态属性
 * @param cursorBrush 光标绘制
 */
@Composable
fun GoodTextField()

/**
 * 更方便易用的TextField,适用于输入密码的情况
 * @param value 输入框中的文字
 * @param onValueChange 输入框中文字的变化回调
 * @param passwordIsShow 密码是否可见,false为密文状态
 * @param onPasswordIsShowChange 密码是否可见状态变化的回调
 * @param modifier 修饰
 * @param hint 输入框没有文字时展示的内容
 * @param maxLines 最多能展示多少行文字
 * @param fontSize text和hint的字体大小
 * @param fontColor text的字体颜色
 * @param maxLength 最多能展示多少个文字,ps:由于会截断文字,会导致截断时重置键盘状态(TextField特性)
 * @param contentAlignment text和hint对其方式
 * @param leading 展示在左边的组件
 * @param trailing 展示在右边的组件,默认是可点击的眼睛图标,用于切换密码是否可见
 * @param background 背景
 * @param horizontalPadding 横向的内间距
 * @param enabled 是否可输入,false无法输入和复制
 * @param readOnly 是否可输入,true无法输入,但可复制,获取焦点,移动光标
 * @param textStyle 字体样式
 * @param keyboardOptions 键盘配置
 * @param keyboardActions 键盘回调
 * @param passwordChar 密码不可见时展示的字符
 * @param visualTransformation 文本展示的转换
 * @param onTextLayout 计算新文本布局时执行的回调
 * @param interactionSource 状态属性
 * @param cursorBrush 光标绘制
 */
@Composable
fun PasswordTextField()
```

## MenuFloatingActionButton

<div align=center><img src="md_resource/fab.gif" width=20%></div>

```kotlin
/**
 * 带菜单的Fab
 * @param icon 菜单图标
 * @param label 菜单提示文本
 * @param srcIconColor 图标颜色
 * @param labelTextColor 提示文本内容颜色
 * @param labelBackgroundColor 提示文本内容区域背景色
 * @param fabBackgroundColor Fab按钮背景色
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
 * 可伸缩顶部导航栏
 * @param title 顶部导航栏标题
 * @param background 背景图片
 * @param modifier 修饰
 * @param onScrollStop 停止滚动时回调
 * @param minScrollPosition 最小滚动位置(距离指定方向的顶点)
 * @param maxScrollPosition 最大滚动位置(距离指定方向的顶点)
 * @param navigationIcon 顶部导航栏图标，默认为返回键
 * @param composePosition 设置bar布局所在的位置,并且间接指定了滑动方向
 * @param chainMode 联动方式
 * @param content compose内容区域,需要内容是在相应方向可滚动的,并且需要自行给内容设置相应方向的PaddingValues或padding
 */
@Composable
fun ScrollableAppBar()

/**
 * 滑动删除控件
 * @param minScrollPosition 最小滚动位置(距离指定方向的顶点)
 * @param maxScrollPosition 最大滚动位置(距离指定方向的顶点)
 * @param backgroundContent 等待拖出的compose内容区域
 * @param modifier 修饰
 * @param contentIsMove compose内容区域是否跟着移动
 * @param content compose内容区域,需要内容是横向可滚动的,并且需要自行给内容设置相应方向的PaddingValues或padding
 */
@Composable
fun SwipeToDismiss()
```

<h6>Finally, thank <a href="https://www.jetbrains.com/?from=ltviews" target="_blank">JetBrains</a>
for its support to this project<h6>
