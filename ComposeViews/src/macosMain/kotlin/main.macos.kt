import androidx.compose.ui.window.Window
import platform.AppKit.NSApp
import platform.AppKit.NSApplication

fun main() {
    NSApplication.ComposeViewsApplication()
    Window("Chat App") {
        ChatAppWithScaffold()
    }
    NSApp?.run()
}
