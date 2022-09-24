//
//  ViewModelStateNullable.swift
//  mokoMvvmFlow
//
//  Created by mdubkov on 25.09.2022.
//

import MultiPlatformLibrary
import SwiftUI

extension ObservableObject where Self: ViewModel {
    func stateNullable<T, R>(
        _ flowKey: KeyPath<Self, CStateFlow<T>>,
        equals: @escaping (T?, T?) -> Bool,
        mapper: @escaping (T?) -> R?
    ) -> R? {
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
        
        return mapper(stateFlow.value)
    }
    
    func stateNullable(_ flowKey: KeyPath<Self, CStateFlow<StringDesc>>) -> String? {
        return stateNullable(
            flowKey,
            equals: { $0 === $1 },
            mapper: { $0?.localized() }
        )
    }
}
