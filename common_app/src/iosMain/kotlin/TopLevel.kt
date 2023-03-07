import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

private const val IMG_FILE_BEGIN = "drawable-xxhdpi/"
private const val IMG_FILE_ENDING = ".png"

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
@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun resourcePainter(imageName: String): Painter {
    return painterResource(remember(imageName) { IMG_FILE_BEGIN + imageName + IMG_FILE_ENDING })
}

actual fun getTestIndex(): Int {
    return -1
}