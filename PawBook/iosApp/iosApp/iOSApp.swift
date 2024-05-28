import SwiftUI
import ComposeApp

@main
struct iOSApp: App {

    init() {
        ComposeApp.HelperKt.doInitKoin()
    }
    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
