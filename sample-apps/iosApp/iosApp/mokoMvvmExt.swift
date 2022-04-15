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
import Combine

// MARK: ViewModel: ObservableObject

//@resultBuilder
//struct LiveDataObserverBuilder {
//    static func buildBlock() -> [LiveData<AnyObject>] { [] }
//}
//
//extension LiveDataObserverBuilder {
//    static func buildBlock(_ settings: LiveData<AnyObject>...) -> [LiveData<AnyObject>] {
//        settings
//    }
//}

extension ObservableObject where Self: ViewModel {
    
//    func observed(
//        @LiveDataObserverBuilder _ content: (Self) -> [LiveData<AnyObject>]
//    ) -> Self {
//        let allLiveData: [LiveData<AnyObject>] = content(self)
//
//        for liveData in allLiveData {
//            liveData.addObserver { _ in
//                self.objectWillChange.send()
//            }
//        }
//
//        return self
//    }
    
//    func binding<T, R>(
//        _ liveDataKey: KeyPath<Self, MutableLiveData<T>>,
//        equals: @escaping (T?, T?) -> Bool,
//        getMapper: @escaping (T) -> R,
//        setMapper: @escaping (R) -> T
//    ) -> Binding<R> {
//        let liveData = self[keyPath: liveDataKey]
//        var lastValue = liveData.value
//
//        var observer: (T?) -> Void = { _ in }
//        observer = { value in
//            if !equals(lastValue, value) {
//                lastValue = value
//                self.objectWillChange.send()
//                liveData.removeObserver(observer: observer)
//            }
//        }
//        liveData.addObserver(observer: observer)
//
//        return Binding(
//            get: { getMapper(liveData.value!) },
//            set: { liveData.value = setMapper($0) }
//        )
//    }
    
//    func binding(_ liveDataKey: KeyPath<Self, MutableLiveData<NSString>>) -> Binding<String> {
//        return binding(
//            liveDataKey,
//            equals: { $0 == $1 },
//            getMapper: { $0 as String },
//            setMapper: { $0 as NSString }
//        )
//    }
//
//    func state<T, R>(
//        _ liveDataKey: KeyPath<Self, LiveData<T>>,
//        equals: @escaping (T?, T?) -> Bool,
//        mapper: @escaping (T) -> R
//    ) -> R {
//        let liveData = self[keyPath: liveDataKey]
//        var lastValue = liveData.value
//
//        var observer: (T?) -> Void = { _ in }
//        observer = { value in
//            if !equals(lastValue, value) {
//                lastValue = value
//                self.objectWillChange.send()
//                liveData.removeObserver(observer: observer)
//            }
//        }
//        liveData.addObserver(observer: observer)
//
//        return mapper(liveData.value!)
//    }
//
//    func state(_ liveDataKey: KeyPath<Self, LiveData<KotlinBoolean>>) -> Bool {
//        return state(
//            liveDataKey,
//            equals: { $0?.boolValue == $1?.boolValue },
//            mapper: { $0.boolValue }
//        )
//    }
    
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

func createPublisher<T>(_ cFlow: CFlow<T>) -> AnyPublisher<T, Never> {
    return CFlowPublisher(cFlow: cFlow).eraseToAnyPublisher()
}

private struct CFlowPublisher<Output: AnyObject>: Publisher {
    
    typealias Output = Output
    typealias Failure = Never
    
    let cFlow: CFlow<Output>
    
    func receive<S>(subscriber: S) where S : Subscriber, Failure == S.Failure, Output == S.Input {
        subscriber.receive(subscription: CFlowSubscription(flow: cFlow, subscriber: subscriber))
    }
}

private class CFlowSubscription<Output: AnyObject, S: Subscriber>: Subscription where S.Input == Output, S.Failure == Never {
    
    private let disposable: DisposableHandle
    private let subscriber: S
    
    init(flow: CFlow<Output>, subscriber: S) {
        self.subscriber = subscriber
        self.disposable = flow.subscribe { value in
            let _ = subscriber.receive(value!)
        }
    }
    
    func request(_ demand: Subscribers.Demand) { }
    
    func cancel() {
        DispatchQueue.main.async {
            self.disposable.dispose()
        }
    }
}

extension ViewModel: ObservableObject {
    
}

// MARK: binding

//func binding<T, R>(
//    _ liveData: MutableLiveData<T>,
//    getMapper: @escaping (T) -> R,
//    setMapper: @escaping (R) -> T
//) -> Binding<R> {
//    return Binding(
//        get: { getMapper(liveData.value!) },
//        set: { liveData.value = setMapper($0) }
//    )
//}
//
//func binding(_ liveData: MutableLiveData<NSString>) -> Binding<String> {
//    return binding(
//        liveData,
//        getMapper: { $0 as String },
//        setMapper: { $0 as NSString }
//    )
//}
//
//func binding(_ liveData: MutableLiveData<KotlinBoolean>) -> Binding<Bool> {
//    return binding(
//        liveData,
//        getMapper: { $0.boolValue },
//        setMapper: { KotlinBoolean(bool: $0) }
//    )
//}
//
//func binding(_ liveData: MutableLiveData<KotlinInt>) -> Binding<Int> {
//    return binding(
//        liveData,
//        getMapper: { $0.intValue },
//        setMapper: { KotlinInt(integerLiteral: $0) }
//    )
//}
//
//func binding(_ liveData: MutableLiveData<KotlinLong>) -> Binding<Int64> {
//    return binding(
//        liveData,
//        getMapper: { $0.int64Value },
//        setMapper: { KotlinLong(longLong: $0) }
//    )
//}
//
//func binding(_ liveData: MutableLiveData<KotlinFloat>) -> Binding<Float> {
//    return binding(
//        liveData,
//        getMapper: { $0.floatValue },
//        setMapper: { KotlinFloat(float: $0) }
//    )
//}
//
//func binding(_ liveData: MutableLiveData<KotlinDouble>) -> Binding<Double> {
//    return binding(
//        liveData,
//        getMapper: { $0.doubleValue },
//        setMapper: { KotlinDouble(double: $0) }
//    )
//}

// MARK: state

//func state<T, R>(
//    _ liveData: LiveData<T>,
//    mapper: @escaping (T) -> R
//) -> R {
//    return mapper(liveData.value!)
//}
//
//func state(_ liveData: LiveData<KotlinBoolean>) -> Bool {
//    return state(liveData, mapper: { $0.boolValue })
//}
//
//func state(_ liveData: LiveData<NSString>) -> String {
//    return state(liveData, mapper: { $0 as String })
//}
//
//func state(_ liveData: LiveData<StringDesc>) -> String {
//    return state(liveData, mapper: { $0.localized() })
//}
//
//func state(_ liveData: LiveData<KotlinInt>) -> Int {
//    return state(liveData, mapper: { $0.intValue })
//}
//
//func state(_ liveData: LiveData<KotlinLong>) -> Int64 {
//    return state(liveData, mapper: { $0.int64Value })
//}
//
//func state(_ liveData: LiveData<KotlinFloat>) -> Float {
//    return state(liveData, mapper: { $0.floatValue })
//}
//
//func state(_ liveData: LiveData<KotlinDouble>) -> Double {
//    return state(liveData, mapper: { $0.doubleValue })
//}
