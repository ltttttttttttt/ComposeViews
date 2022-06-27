# ComposeViews

jatpack compose views, in to Android,Web,Desktop,Ios...

views:
1.ComposePager 2.Banner 3.BannerIndicator 4.ImageBanner 5.RefreshLayout

# ComposePager

```kotlin
/**
 * 类似于xml中的ViewPager
 * [pageCount]一共有多少页
 * [modifier]修饰
 * [composePagerState]ComposePager的状态
 * [orientation]滑动的方向
 * [content]compose内容区域
 */
@Composable
fun ComposePager(
    pageCount: Int,
    modifier: Modifier = Modifier,
    composePagerState: ComposePagerState = rememberComposePagerState(),
    orientation: Orientation = Orientation.Horizontal,
    content: @Composable ComposePagerScope.() -> Unit
) {
}
```

# Banner

```kotlin

```

# BannerIndicator

```kotlin

```

# ImageBanner

```kotlin

```

# RefreshLayout

```kotlin

```