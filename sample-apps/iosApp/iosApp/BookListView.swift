//
//  BookListView.swift
//  iosApp
//
//  Created by Aleksey Mikhailov on 18.03.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import MultiPlatformLibrary

struct BookListViewBody: View {
    let state: BookListViewModelStateKs
    let onRetryPressed: () -> Void
    
    var body: some View {
        switch(state) {
        case .loading:
            ProgressView()
        case .empty(let data):
            Text(data.message.localized())
        case .error(let data):
            VStack {
                Text(data.message.localized())
                Button("Retry") {
                    onRetryPressed()
                }
            }
        case .success(let data):
            List(
                data.items,
                id: \.id
            ) { unit in
                let unit = BookListViewModelListUnitKs(unit)
                switch(unit) {
                case .bookUnit(let bookUnit):
                    Button(
                        action: { bookUnit.onPressed() },
                        label: { Text(bookUnit.title) }
                    ).buttonStyle(DefaultButtonStyle())
                case .advertUnit(let adUnit):
                    Button(
                        action: { adUnit.onPressed() },
                        label: { Text(adUnit.text) }
                    ).buttonStyle(DefaultButtonStyle())
                }
            }
        }
    }
}

struct BookListViewBody_Previews: PreviewProvider {
    struct Preview: View {
        var body: some View {
            BookListViewBody(
                state: .loading,
                onRetryPressed: {}
            )
            BookListViewBody(
                state: .empty(BookListViewModelStateEmpty(message: "empty".desc())),
                onRetryPressed: {}
            )
            BookListViewBody(
                state: .error(BookListViewModelStateError(message: "error".desc())),
                onRetryPressed: {}
            )
            BookListViewBody(
                state: .success(
                    BookListViewModelStateSuccess(
                        items: [
                            BookListViewModelListUnitBookUnit(id: "1", title: "test", onPressed: {}),
                            BookListViewModelListUnitAdvertUnit(id: "2", text: "advert", onPressed: {})
                        ]
                    )
                ),
                onRetryPressed: {}
            )
        }
    }
    
    static var previews: some View {
        Group {
            Preview()
        }.previewDisplayName("Light theme")
            .preferredColorScheme(.light)
        
        Group {
            Preview()
        }.previewDisplayName("Dark theme")
            .preferredColorScheme(.dark)
    }
}
