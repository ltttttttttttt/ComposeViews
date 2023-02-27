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

package com.lt.compose_views.chain_scrollable_component

import androidx.compose.foundation.ExperimentalFoundationApi

/**
 * creator: lt  2022/9/29  lt.dygzs@qq.com
 * effect : [ChainScrollableComponent]的联动方式
 *          Chain mode of the [ChainScrollableComponent]
 * warning:
 */
enum class ChainMode {
    /**
     * 内容区域优先
     * Content first
     */
    @ExperimentalFoundationApi//todo 暂时有问题,先不放开
    ContentFirst,

    /**
     * 联动区域优先
     * Chain content first
     */
    ChainContentFirst,
}