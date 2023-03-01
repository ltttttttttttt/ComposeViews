import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

/**
 * 加载网络图片
 */
@Composable
actual fun rememberPainter(data: String?): Painter {
    TODO()
}

/**
 * 根据图片文件名加载图片
 */
@Composable
actual fun resourcePainter(imageName: String): Painter {
    TODO()
}

actual fun getTestIndex(): Int {
    return -1
}