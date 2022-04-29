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
}
