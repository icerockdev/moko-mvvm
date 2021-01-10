/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import Foundation

fileprivate var observersContext: UInt8 = 0

public protocol HasLiveDataObservers: class {
  var observers: Array<AnyObject> { get set }
}

public extension HasLiveDataObservers {
  fileprivate func synchronizedObservers<T>( _ action: () -> T) -> T {
    objc_sync_enter(self)
    let result = action()
    objc_sync_exit(self)
    return result
  }
  
  public var observers: Array<AnyObject> {
    get {
      return synchronizedObservers {
        if let observers = objc_getAssociatedObject(self, &observersContext) as? Array<AnyObject> {
          return observers
        }
        let observers = Array<AnyObject>()
        objc_setAssociatedObject(self, &observersContext, observers, .OBJC_ASSOCIATION_RETAIN_NONATOMIC)
        return observers
      }
    }
    set {
      synchronizedObservers {
        objc_setAssociatedObject(self, &observersContext, newValue, .OBJC_ASSOCIATION_RETAIN_NONATOMIC)
      }
    }
  }
  
  public func clear() {
    observers.removeAll()
  }
  
  func addObserver(_ observer: AnyObject) {
    observers.append(observer)
  }
}
