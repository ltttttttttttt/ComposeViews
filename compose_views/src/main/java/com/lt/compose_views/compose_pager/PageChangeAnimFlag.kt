package com.lt.compose_views.compose_pager

//内部使用的翻页标志位
internal sealed class PageChangeAnimFlag {
    //下一页
    object Next : PageChangeAnimFlag()

    //上一页
    object Prev : PageChangeAnimFlag()

    //还原offset
    object Reduction : PageChangeAnimFlag()

    //跳转到某一页,无动画
    class GoToPageNotAnim(val index: Int) : PageChangeAnimFlag()
}