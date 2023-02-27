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

package com.lt.common_app

import M
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.lt.common_app.base.BaseComposeActivity
import com.lt.compose_views.other.FpsText
import com.lt.compose_views.value_selector.ValuesSelector
import com.lt.compose_views.value_selector.ValueSelectState
import com.lt.compose_views.value_selector.ValueSelector
import com.lt.compose_views.value_selector.date_selector.DateSelector
import com.lt.compose_views.value_selector.date_selector.DateSelectorState
import com.lt.compose_views.value_selector.rememberValueSelectState

/**
 * creator: lt  2022/12/3  lt.dygzs@qq.com
 * effect :
 * warning:
 */
class DateSelectorA : BaseComposeActivity() {
    @Composable
    override fun ComposeContent() {
        Column(M.fillMaxSize().verticalScroll(rememberScrollState())) {
            FpsText()
            Value()
//            Values()
            Date()
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun Date() {
        val state = remember { DateSelectorState(2022, 2, 5) }
        DateSelector(state)
        Button({
            "${state.getYear()}.${state.getMonth()}.${state.getDay()}".showToast()
        }) {
            Text("获取当前值")
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun Values() {
        val values = remember {
            listOf(
                ArrayList((1900..2022).map(Int::toString)),
                ArrayList((1..12).map(Int::toString)),
            )
        }
        val states = remember {
            listOf(
                ValueSelectState(),
                ValueSelectState(),
            )
        }
        ValuesSelector(
            values,
            states,
        )
        Button({
            values.mapIndexed { index, strings ->
                strings[states[index].getSelectIndex()]
            }.joinToString().showToast()
        }) {
            Text("获取当前值")
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun Value() {
        val values = remember { ArrayList((0 until 60).map(Int::toString)) }
        val state = rememberValueSelectState()
        ValueSelector(
            values = values,
            state = state,
            defaultSelectIndex = 10,
            //isLoop = true,
            //cacheSize = 4,
            //textColors = arrayListOf(
            //    Color.Black, Color.Red, Color.Green, Color.Blue
            //),
            //textSizes = arrayListOf(
            //    16.sp, 14.sp, 12.sp, 10.sp,
            //)
        )
        Button({
            values[state.getSelectIndex()].showToast()
        }) {
            Text("获取当前值")
        }
    }
}