import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.lt.ltttttttttttt.common_app.generated.resources.Res
import com.lt.ltttttttttttt.common_app.generated.resources.back
import org.jetbrains.compose.resources.painterResource

/**
 * 加载网络图片
 */
@Composable
actual fun rememberPainter(data: String?): Painter {
    //todo 图片加载
    return painterResource(Res.drawable.back)
}