//
//  Routing.swift
//  iosApp
//
//  Created by Aleksey Mikhailov on 18.03.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

enum Screen {
    case main
    case second
}

enum Route {
    case toMain
    case toSecond(text: String)
    
    var destination: Screen {
        switch(self) {
        case .toMain:
            return Screen.main
        case .toSecond(_):
            return Screen.second
        }
    }
}

class Router {
    var activeRoute: State<Route?>
    let activeRouteBinding: Binding<Screen?>
    
    init() {
        let activeRoute: State<Route?> = State(initialValue: nil)
        
        self.activeRoute = activeRoute
        self.activeRouteBinding = Binding(
            get: { return activeRoute.wrappedValue?.destination },
            set: { newValue in
                if let newValue = newValue {
                    fatalError("i can't set \(newValue)")
                } else {
                    activeRoute.wrappedValue = nil
                }
            }
        )
    }
    
    func route(_ destination: Route?) {
        self.activeRoute.wrappedValue = destination
        self.activeRoute.update()
    }
}

func createRouteActiveBinding(route: Binding<Route?>) -> Binding<Bool> {
    return Binding(
        get: {
            route.wrappedValue != nil
        },
        set: { newValue in
            if !newValue {
                route.wrappedValue = nil
            }
        }
    )
}

struct NavigationDestination<Destination>: View where Destination: View {
    @State var isActive: Bool = false
    private let destination: () -> Destination
    
    init(@ViewBuilder destination: @escaping () -> Destination) {
        self.destination = destination
    }
    
    var body: some View {
        NavigationLink(
            isActive: $isActive,
            destination: self.destination,
            label: { EmptyView().hidden() }
        )
    }
}
