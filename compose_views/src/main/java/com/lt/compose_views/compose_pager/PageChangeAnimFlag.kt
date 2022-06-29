package com.lt.compose_views.compose_pager

//内部使用的翻页标志位
internal sealed class PageChangeAnimFlag {
    //下一页
    class Next : PageChangeAnimFlag()

    //上一页
    class Prev : PageChangeAnimFlag()

    //还原offset
    class Reduction : PageChangeAnimFlag()
}