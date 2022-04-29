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

    func binding<T, R>(
        _ flowKey: KeyPath<Self, CMutableStateFlow<T>>,
        equals: @escaping (T?, T?) -> Bool,
        getMapper: @escaping (T) -> R,
        setMapper: @escaping (R) -> T
    ) -> Binding<R> {
        let stateFlow: CMutableStateFlow<T> = self[keyPath: flowKey]
        var lastValue: T? = stateFlow.value
        
        var disposable: DisposableHandle? = nil
        
        disposable = stateFlow.subscribe(onCollect: { value in
            if !equals(lastValue, value) {
                lastValue = value
                self.objectWillChange.send()
                disposable?.dispose()
            }
        })
        
        return Binding(
            get: { getMapper(stateFlow.value!) },
            set: { stateFlow.value = setMapper($0) }
        )
    }
    
    func binding(_ flowKey: KeyPath<Self, CMutableStateFlow<NSString>>) -> Binding<String> {
        return binding(
            flowKey,
            equals: { $0 == $1 },
            getMapper: { $0 as String },
            setMapper: { $0 as NSString }
        )
    }
}
