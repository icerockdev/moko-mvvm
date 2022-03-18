//
//  BookListViewBinding.swift
//  iosApp
//
//  Created by Aleksey Mikhailov on 18.03.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared


struct BookListView: View {
    @ObservedObject var viewModel: BookListViewModel = BookListViewModel()
    
    var body: some View {
        BookListViewBody(
            state: viewModel.state(
                { $0.state },
                equals: { $0 === $1 },
                mapper: { $0 }
            ),
            onRetryPressed: {
                viewModel.onRetryPressed()
            }
        ).onAppear {
            viewModel.start()
        }.onDisappear {
            viewModel.onCleared()
        }.onReceive(publisher(viewModel.actions)) { action in
            if let routeToDetails = action as? BookListViewModelActionRouteToBookDetails {
                print(routeToDetails.id)
                // here should be routing
            } else if let openUrl = action as? BookListViewModelActionOpenUrl {
                UIApplication.shared.open(URL(string: openUrl.url)!)
            } else {
                fatalError()
            }
        }.navigationTitle("Books")
    }
}
