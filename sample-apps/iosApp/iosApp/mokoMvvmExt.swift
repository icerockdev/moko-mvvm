//
//  mokoMvvmExt.swift
//  iosApp
//
//  Created by Aleksey Mikhailov on 18.03.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

// MARK: ViewModel: ObservableObject

@resultBuilder
struct LiveDataObserverBuilder {
    static func buildBlock() -> [LiveData<AnyObject>] { [] }
}

extension LiveDataObserverBuilder {
    static func buildBlock(_ settings: LiveData<AnyObject>...) -> [LiveData<AnyObject>] {
        settings
    }
}

extension ObservableObject where Self: ViewModel {
    
    func observed(
        @LiveDataObserverBuilder _ content: (Self) -> [LiveData<AnyObject>]
    ) -> Self {
        let allLiveData: [LiveData<AnyObject>] = content(self)

        for liveData in allLiveData {
            liveData.addObserver { _ in
                self.objectWillChange.send()
            }
        }
        
        return self
    }
}

extension ViewModel: ObservableObject {
    
}

// MARK: binding

func binding<T, R>(
    _ liveData: MutableLiveData<T>,
    getMapper: @escaping (T) -> R,
    setMapper: @escaping (R) -> T
) -> Binding<R> {
    return Binding(
        get: { getMapper(liveData.value!) },
        set: { liveData.value = setMapper($0) }
    )
}

func binding(_ liveData: MutableLiveData<NSString>) -> Binding<String> {
    return binding(
        liveData,
        getMapper: { $0 as String },
        setMapper: { $0 as NSString }
    )
}

func binding(_ liveData: MutableLiveData<KotlinBoolean>) -> Binding<Bool> {
    return binding(
        liveData,
        getMapper: { $0.boolValue },
        setMapper: { KotlinBoolean(bool: $0) }
    )
}

func binding(_ liveData: MutableLiveData<KotlinInt>) -> Binding<Int> {
    return binding(
        liveData,
        getMapper: { $0.intValue },
        setMapper: { KotlinInt(integerLiteral: $0) }
    )
}

func binding(_ liveData: MutableLiveData<KotlinLong>) -> Binding<Int64> {
    return binding(
        liveData,
        getMapper: { $0.int64Value },
        setMapper: { KotlinLong(longLong: $0) }
    )
}

func binding(_ liveData: MutableLiveData<KotlinFloat>) -> Binding<Float> {
    return binding(
        liveData,
        getMapper: { $0.floatValue },
        setMapper: { KotlinFloat(float: $0) }
    )
}

func binding(_ liveData: MutableLiveData<KotlinDouble>) -> Binding<Double> {
    return binding(
        liveData,
        getMapper: { $0.doubleValue },
        setMapper: { KotlinDouble(double: $0) }
    )
}

// MARK: state

func state<T, R>(
    _ liveData: LiveData<T>,
    mapper: @escaping (T) -> R
) -> R {
    return mapper(liveData.value!)
}

func state(_ liveData: LiveData<KotlinBoolean>) -> Bool {
    return state(liveData, mapper: { $0.boolValue })
}

func state(_ liveData: LiveData<NSString>) -> String {
    return state(liveData, mapper: { $0 as String })
}

func state(_ liveData: LiveData<StringDesc>) -> String {
    return state(liveData, mapper: { $0.localized() })
}

func state(_ liveData: LiveData<KotlinInt>) -> Int {
    return state(liveData, mapper: { $0.intValue })
}

func state(_ liveData: LiveData<KotlinLong>) -> Int64 {
    return state(liveData, mapper: { $0.int64Value })
}

func state(_ liveData: LiveData<KotlinFloat>) -> Float {
    return state(liveData, mapper: { $0.floatValue })
}

func state(_ liveData: LiveData<KotlinDouble>) -> Double {
    return state(liveData, mapper: { $0.doubleValue })
}
