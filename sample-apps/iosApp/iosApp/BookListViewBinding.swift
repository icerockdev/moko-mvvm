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

func createViewModel() -> BookListViewModel {
    return BookListViewModel().start()
}

struct BookListView: View {
    @ObservedObject var viewModel: BookListViewModel = createViewModel()
    @State private var detailOpened: Bool = false
    @State private var detailBookId: Int32? = nil
    
    var body: some View {
        ZStack {
            NavigationLink(isActive: $detailOpened) {
                if let id = detailBookId {
                    BookDetailsView(id: id)
                } else {
                    EmptyView()
                }
            } label: {
                EmptyView()
                    .hidden()
            }

            BookListViewBody(
                state: viewModel.state(
                    \.state,
                    equals: { $0 === $1 },
                    mapper: { BookListViewModelStateKs($0) }
                ),
                onRetryPressed: {
                    viewModel.onRetryPressed()
                }
            ).onReceive(publisher(viewModel.actions)) { action in
                let action = BookListViewModelActionKs(action)
                switch(action) {
                case .routeToBookDetails(let data):
                    detailBookId = data.id
                    detailOpened = true
                case .openUrl(let data):
                    UIApplication.shared.open(URL(string: data.url)!)
                }
            }.navigationTitle("Books")
        }
    }
}
