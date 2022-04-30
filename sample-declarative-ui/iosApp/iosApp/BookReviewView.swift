//
//  BookReviewView.swift
//  iosApp
//
//  Created by Aleksey Mikhailov on 16.04.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct BookReviewView: View {
    let id: Int32
    
    var body: some View {
        Text("Hello, World \(id)!")
    }
}

struct BookReviewView_Previews: PreviewProvider {
    static var previews: some View {
        BookReviewView(id: 10)
    }
}
