import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.lt.compose_views.touch_bar.StarBar
import com.lt.compose_views.util.rememberMutableStateOf

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            var star by rememberMutableStateOf(2)
            StarBar(star, { star = it })
        }
    }
}