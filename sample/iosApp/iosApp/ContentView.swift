import SwiftUI
import sample

struct ContentView: View {
    var body: some View {
        KuiklyRenderViewPage(pageName: "LoginPage", data: [:]).ignoresSafeArea()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}