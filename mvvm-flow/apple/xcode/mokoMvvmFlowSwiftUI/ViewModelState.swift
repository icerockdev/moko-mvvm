//
//  CFlowExt.swift
//  mokoMvvmFlowSwiftUI (iOS)
//
//  Created by Aleksey Mikhailov on 29.04.2022.
//

import MultiPlatformLibrary
import SwiftUI
import Combine

public extension ObservableObject where Self: ViewModel {

    func state<T, R>(
        _ flowKey: KeyPath<Self, CStateFlow<T>>,
        equals: @escaping (T?, T?) -> Bool,
        mapper: @escaping (T) -> R
    ) -> R {
        let stateFlow: CStateFlow<T> = self[keyPath: flowKey]
        var lastValue: T? = stateFlow.value
        
        var disposable: DisposableHandle? = nil
        
        disposable = stateFlow.subscribe(onCollect: { value in
            if !equals(lastValue, value) {
                lastValue = value
                self.objectWillChange.send()
                disposable?.dispose()
            }
        })
        
        return mapper(stateFlow.value!)
    }
    
    func state(_ flowKey: KeyPath<Self, CStateFlow<KotlinBoolean>>) -> Bool {
        return state(
            flowKey,
            equals: { $0?.boolValue == $1?.boolValue },
            mapper: { $0.boolValue }
        )
    }
    
    func state(_ flowKey: KeyPath<Self, CStateFlow<KotlinDouble>>) -> Double {
        return state(
            flowKey,
            equals: { $0?.doubleValue == $1?.doubleValue },
            mapper: { $0.doubleValue }
        )
    }
    
    func state(_ flowKey: KeyPath<Self, CStateFlow<KotlinFloat>>) -> Float {
        return state(
            flowKey,
            equals: { $0?.floatValue == $1?.floatValue },
            mapper: { $0.floatValue }
        )
    }
    
    func state(_ flowKey: KeyPath<Self, CStateFlow<KotlinInt>>) -> Int {
        return state(
            flowKey,
            equals: { $0?.intValue == $1?.intValue },
            mapper: { $0.intValue }
        )
    }
    
    func state(_ flowKey: KeyPath<Self, CStateFlow<KotlinLong>>) -> Int64 {
        return state(
            flowKey,
            equals: { $0?.int64Value == $1?.int64Value },
            mapper: { $0.int64Value }
        )
    }
    
    func state(_ flowKey: KeyPath<Self, CStateFlow<NSString>>) -> String {
        return state(
            flowKey,
            equals: { $0 == $1 },
            mapper: { $0 as String }
        )
    }
    
    func state<T>(_ flowKey: KeyPath<Self, CStateFlow<NSArray>>) -> Array<T> {
        return state(
            flowKey,
            equals: { $0 === $1 },
            mapper: { $0 as! Array<T> }
        )
    }
}
