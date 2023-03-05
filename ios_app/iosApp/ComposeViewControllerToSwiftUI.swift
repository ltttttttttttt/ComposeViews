import UIKit
import SwiftUI
import ComposeViews

struct ComposeViewControllerToSwiftUI: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        return Main_iosKt.ChatViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}
