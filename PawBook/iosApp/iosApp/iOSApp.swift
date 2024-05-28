import SwiftUI
import ComposeApp

@main
struct iOSApp: App {

    init() {
        ComposeApp.KoinModule.doInitKoin()
    }
    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
