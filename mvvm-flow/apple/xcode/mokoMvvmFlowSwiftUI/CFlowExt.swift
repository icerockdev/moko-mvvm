//
//  CFlowExt.swift
//  mokoMvvmFlowSwiftUI (iOS)
//
//  Created by Aleksey Mikhailov on 29.04.2022.
//

import MultiPlatformLibrary
import Combine

public func createPublisher<T>(_ cFlow: CFlow<T>) -> AnyPublisher<T, Never> {
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
        // TRICKY: `as! CFlow<AnyObject>` cast here, and `as! Output` cast below, combine
        // to work around https://github.com/apple/swift/issues/65331
        self.disposable = (flow as! CFlow<AnyObject>).subscribe { value in
            let _ = subscriber.receive(value as! Output)
        }
    }
    
    func request(_ demand: Subscribers.Demand) { }
    
    func cancel() {
        DispatchQueue.main.async {
            self.disposable.dispose()
        }
    }
}
