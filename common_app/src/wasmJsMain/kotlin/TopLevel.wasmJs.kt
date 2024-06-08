import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

/**
 * 加载网络图片
 */
@Composable
actual fun rememberPainter(data: String?): Painter {
    //todo 图片加载
    return resourcePainter("back")
}