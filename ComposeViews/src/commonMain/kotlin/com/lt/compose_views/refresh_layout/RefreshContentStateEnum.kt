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

package com.lt.compose_views.refresh_layout

/**
 * creator: lt  2022/9/1  lt.dygzs@qq.com
 * effect : 刷新布局内容区域的组件状态
 *          State of refreshed content area
 * warning:
 */
enum class RefreshContentStateEnum {
    /**
     * 刷新状态已停止,表示刷新布局已经被收回不可见,此时应该停止重组
     * State of refresh stop
     */
    Stop,

    /**
     * 刷新中,此时可以做刷新的动画
     * State of refreshing
     */
    Refreshing,

    /**
     * 用户拖拽中,刷新布局已经被拽出
     * The state the user is dragging
     */
    Dragging,
}