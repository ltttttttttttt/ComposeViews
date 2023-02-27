/*
 * Copyright lt 2023
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    //跳转到某一页,有动画
    class GoToPageWithAnim(val index: Int) : PageChangeAnimFlag()
}