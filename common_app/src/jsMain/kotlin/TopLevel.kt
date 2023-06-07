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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.*
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Int8Array
import org.w3c.xhr.ARRAYBUFFER
import org.w3c.xhr.XMLHttpRequest
import org.w3c.xhr.XMLHttpRequestResponseType
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val IMG_FILE_BEGIN = "drawable-xxhdpi/"
private const val IMG_FILE_ENDING = ".webp"

/**
 * 加载网络图片
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun rememberPainter(data: String?): Painter {
    return BitmapPainter(remember(data) { JSUrlResourceImpl(data ?: "") }.rememberImageBitmap().orEmpty())
}

/**
 * 根据图片文件名加载图片
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun resourcePainter(imageName: String): Painter {
    return painterResource(remember(imageName) { IMG_FILE_BEGIN + imageName + IMG_FILE_ENDING })
}

actual fun getTestIndex(): Int {
    return -1
}

@ExperimentalResourceApi
private class JSUrlResourceImpl(val path: String) : Resource {
    override suspend fun readBytes(): ByteArray {
        return suspendCoroutine { continuation ->
            val req = XMLHttpRequest()
            req.open("GET", path, true)
            req.responseType = XMLHttpRequestResponseType.ARRAYBUFFER

            req.onload = { event ->
                val arrayBuffer = req.response
                if (arrayBuffer is ArrayBuffer) {
                    continuation.resume(arrayBuffer.toByteArray())
                } else {
                    continuation.resumeWithException(RuntimeException("Missing resource with path: $path"))
                }
            }
            req.send(null)
        }
    }

    private fun ArrayBuffer.toByteArray() = Int8Array(this, 0, byteLength).unsafeCast<ByteArray>()
}