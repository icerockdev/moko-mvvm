//
//  ViewModelBinding.swift
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
        
        disposable = stateFlow.subscribe(onCollect: { [weak self] value in
            if !equals(lastValue, value) {
                lastValue = value
                self?.objectWillChange.send()
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
    
    func binding(_ flowKey: KeyPath<Self, CMutableStateFlow<KotlinBoolean>>) -> Binding<Bool> {
        return binding(
            flowKey,
            equals: { $0?.boolValue == $1?.boolValue },
            getMapper: { $0.boolValue },
            setMapper: { KotlinBoolean(bool: $0) }
        )
    }
    
    func binding(_ flowKey: KeyPath<Self, CMutableStateFlow<KotlinInt>>) -> Binding<Int> {
        return binding(
            flowKey,
            equals: { $0?.intValue == $1?.intValue },
            getMapper: { $0.intValue },
            setMapper: { KotlinInt(int: Int32($0)) }
        )
    }
    
    func binding(_ flowKey: KeyPath<Self, CMutableStateFlow<KotlinLong>>) -> Binding<Int64> {
        return binding(
            flowKey,
            equals: { $0?.int64Value == $1?.int64Value },
            getMapper: { $0.int64Value },
            setMapper: { KotlinLong(longLong: $0) }
        )
    }
    
    func binding(_ flowKey: KeyPath<Self, CMutableStateFlow<KotlinFloat>>) -> Binding<Float> {
        return binding(
            flowKey,
            equals: { $0?.floatValue == $1?.floatValue },
            getMapper: { $0.floatValue },
            setMapper: { KotlinFloat(float: $0) }
        )
    }
    
    func binding(_ flowKey: KeyPath<Self, CMutableStateFlow<KotlinDouble>>) -> Binding<Double> {
        return binding(
            flowKey,
            equals: { $0?.doubleValue == $1?.doubleValue },
            getMapper: { $0.doubleValue },
            setMapper: { KotlinDouble(double: $0) }
        )
    }
}
