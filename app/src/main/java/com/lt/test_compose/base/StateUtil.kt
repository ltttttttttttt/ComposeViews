package util.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * creator: lt  2021/4/14  lt.dygzs@qq.com
 * effect : 状态工具类
 * warning:
 */

/**
 * 快捷使用remember { mutableStateOf(T) }
 */
@Composable
fun <T> rememberMutableStateOf(value: T): MutableState<T> = remember { mutableStateOf(value) }

//根据key(和方法:默认)来记录数据
@Composable
fun <T> rememberMutableStateOf(key: Any?, value: T): MutableState<T> = remember(key) { mutableStateOf(value) }