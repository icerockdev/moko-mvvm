//
//  BookListView.swift
//  iosApp
//
//  Created by Aleksey Mikhailov on 18.03.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

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
            List(data.items, id: \.id) { unit in
                let unit = BookListViewModelListUnitKs(unit)
                switch(unit) {
                case .bookUnit(let bookUnit):
                    Text(bookUnit.title).onTapGesture {
                        bookUnit.onPressed()
                    }
                case .advertUnit(let adUnit):
                    Text(adUnit.text).onTapGesture {
                        adUnit.onPressed()
                    }
                }
            }
        }
    }
}
