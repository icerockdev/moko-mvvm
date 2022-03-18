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
    let state: BookListViewModelState
    let onRetryPressed: () -> Void
    
    var body: some View {
        if let _ = state as? BookListViewModelStateLoading {
            ProgressView()
        } else if let empty = state as? BookListViewModelStateEmpty {
            Text(empty.message.localized())
        } else if let error = state as? BookListViewModelStateError {
            VStack {
                Text(error.message.localized())
                Button("Retry") {
                    onRetryPressed()
                }
            }
        } else if let data = state as? BookListViewModelStateSuccess {
            List(data.items, id: \.id) { unit in
                if let bookUnit = unit as? BookListViewModelListUnitBookUnit {
                    Text(bookUnit.title).onTapGesture {
                        bookUnit.onPressed()
                    }
                } else if let adUnit = unit as? BookListViewModelListUnitAdvertUnit {
                    Text(adUnit.text).onTapGesture {
                        adUnit.onPressed()
                    }
                } else {
                    fatalError()
                }
            }
        } else {
            fatalError()
        }
    }
}
