import androidx.compose.material.Text
import androidx.compose.ui.window.Application
import platform.UIKit.UIViewController

fun ComposeViewController(): UIViewController =
    Application("Chat") {
        Text("Hello World! --ios")
    }
