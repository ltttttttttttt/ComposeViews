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
 * 类似于xml中的ViewPager
 * @param pageCount 一共有多少页
 * @param modifier 修饰
 * @param composePagerState ComposePager的状态
 * @param orientation 滑动的方向
 * @param userEnable 用户是否可以滑动,等于false时用户滑动无反应,但代码可以执行翻页
 * @param pageCache 左右两边的页面缓存,默认左右各缓存1页,但不能少于1页(不宜过大)
 * @param scrollableInteractionSource 滚动状态监听,可以用来监听:用户开始(结束,取消)滑动等事件,使用可以参考[Banner]
 * @param content compose内容区域
 */
@Composable
fun ComposePager()
```

## Banner

```kotlin
/**
 * 可以自动循环轮播的ComposePager
 * @param pageCount 一共有多少页
 * @param modifier 修饰
 * @param bannerState Banner的状态
 * @param orientation 滑动的方向
 * @param userEnable 用户是否可以滑动,等于false时用户滑动无反应,但代码可以执行翻页
 * @param autoScroll 是否自动滚动
 * @param autoScrollTime 自动滚动间隔时间
 * @param content compose内容区域
 */
@Composable
fun Banner()
```

## PagerIndicator

<div align=center><img src="md_resource/image_banner.gif" width=50%></div>

<div align=center><img src="md_resource/text_pager_indicator.gif" width=50%></div>

```kotlin
/**
 * 适用于Pager的指示器
 * @param size 指示器数量
 * @param offsetPercentWithSelect 选中的指示器的偏移百分比
 * @param selectIndex 选中的索引
 * @param indicatorItem 未被选中的指示器
 * @param selectIndicatorItem 被选中的指示器
 * @param modifier 修饰
 * @param margin 指示器之间的间距
 * @param orientation 指示器排列方向
 * @param userCanScroll 用户是否可以滚动
 */
@Composable
fun PagerIndicator()

/**
 * 适用于Pager的文本指示器
 * @param texts 文本列表
 * @param offsetPercentWithSelect 选中的指示器的偏移百分比
 * @param selectIndex 选中的索引
 * @param fontSize 未被选中的文字大小
 * @param selectFontSize 被选中的文字大小
 * @param textColor 未被选中的文字颜色
 * @param selectTextColor 被选中的文字颜色
 * @param selectIndicatorColor 指示器的颜色
 * @param onIndicatorClick 指示器的点击事件
 * @param modifier 修饰
 * @param margin 指示器之间的间距(两边也有,保证即使选中的指示器较大,也不容易超出控件区域)
 * @param userCanScroll 用户是否可以滚动
 */
@Composable
fun TextPagerIndicator()
```

## ImageBanner

```kotlin
/**
 * 展示图片的Banner
 * @param imageSize 图片数量
 * @param imageContent 放置图片的content
 * @param indicatorItem 未被选中的指示器,如果为null则不展示指示器
 * @param selectIndicatorItem 被选中的指示器,如果为null则不展示指示器
 * @param modifier 修饰
 * @param bannerState Banner的状态
 * @param orientation 滑动的方向
 * @param autoScroll 是否自动滚动
 * @param autoScrollTime 自动滚动间隔时间
 */
@Composable
fun ImageBanner()
```

## RefreshLayout

<div align=center><img src="md_resource/refresh_layout.gif" width=30%></div>

```kotlin
/**
 * 可以任意方向拖动刷新的容器
 * @param refreshContent 刷新布局内容区域
 * @param refreshLayoutState RefreshLayout的状态,可以调用[rememberRefreshLayoutState]方法创建state并传入一个刷新时触发的回调
 * @param modifier 修饰
 * @param refreshContentThreshold 刷新布局拖动的阈值,拖动超过多少松开才算真的刷新,如果为null,表示为[refreshContent]的宽或高
 * @param composePosition 设置刷新布局所在的位置,并且间接指定了滑动方向
 * @param contentIsMove content组件是否在刷新时跟着移动,true的效果类似于PullToRefresh,false的效果类似于SwipeRefreshLayout
 * @param dragEfficiency 拖动的'有效率',比如默认是手指拖动20px,只能拖出10px
 * @param isSupportCanNotScrollCompose 是否需要支持无法滚动的组件,为true的话内部会套一层可滚动组件
 * @param userEnable 用户是否可以拖动,等于false时用户拖动无反应,但代码可以修改刷新状态
 * @param content compose内容区域
 */
@Composable
fun RefreshLayout()

/**
 * 下拉刷新
 * @param refreshLayoutState RefreshLayout的状态
 * @param modifier 修饰
 * @param refreshContent 刷新布局内容区域
 * @param content compose内容区域
 */
@Composable
fun PullToRefresh()

/**
 * 下拉刷新+上拉加载,如果内部不支持上下滑动的话,则无法使用(可以给modifier加上[verticalScroll]修饰)
 * @param topRefreshLayoutState top的刷新布局的state,可以调用[rememberRefreshLayoutState]方法创建state并传入一个刷新时触发的回调
 * @param bottomRefreshLayoutState bottom的刷新布局的state,可以调用[rememberRefreshLayoutState]方法创建state并传入一个刷新时触发的回调
 * @param modifier 修饰
 * @param topRefreshContent top的刷新布局的content,有默认样式,可以传入lambda自定义
 * @param bottomIsLoadFinish bottom刷新布局是否刷新完成
 * @param bottomRefreshContent bottom的刷新布局的content,有默认样式,可以传入lambda自定义
 * @param content 内容
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
