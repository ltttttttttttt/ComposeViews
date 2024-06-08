import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import coil3.compose.rememberAsyncImagePainter

/**
 * 加载网络图片
 */
@Composable
actual fun rememberPainter(data: String?): Painter = rememberAsyncImagePainter(data)