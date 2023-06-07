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

package com.lt.compose_views.pager_indicator

import androidx.compose.runtime.Stable

/**
 * creator: lt  2022/10/25  lt.dygzs@qq.com
 * effect : [PagerIndicator]的compose作用域
 *          Compose scope of the [PagerIndicator]
 * warning:
 */
@Stable
class PagerIndicatorScope(
    /**
     * 指示器列表的信息
     * Info of the indicators
     */
    val indicatorsInfo: IndicatorsInfo,
)