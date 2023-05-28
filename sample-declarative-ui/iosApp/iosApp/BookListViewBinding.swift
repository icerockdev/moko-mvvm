//
//  BookListViewBinding.swift
//  iosApp
//
//  Created by Aleksey Mikhailov on 18.03.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import MultiPlatformLibrary
import mokoMvvmFlowSwiftUI
import Combine

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
                    BookReviewView(id: id)
                } else {
                    EmptyView()
                }
            } label: {
                EmptyView()
                    .hidden()
            }
            
            ForEach(viewModel.listTestSwift, id: \.self) { item in
                Text(item)
            }

            BookListViewBody(
                state: viewModel.stateKs,
                onRetryPressed: {
                    viewModel.onRetryPressed()
                }
            ).onReceive(viewModel.actionsKs) { action in
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

extension BookListViewModel {
    var stateKs: BookListViewModelStateKs {
        get {
            return self.state(
                \.state,
                equals: { $0 === $1 },
                mapper: { BookListViewModelStateKs($0) }
            )
        }
    }
    
    var listTestSwift: [String] {
        get {
            return self.state(
                \.listTest,
                 equals: { $0 === $1 },
                 mapper: { $0 as! [String] }
            )
        }
    }
    
    var actionsKs: AnyPublisher<BookListViewModelActionKs, Never> {
        get {
            return createPublisher(self.actions)
                .map { BookListViewModelActionKs($0) }
                .eraseToAnyPublisher()
        }
    }
}
