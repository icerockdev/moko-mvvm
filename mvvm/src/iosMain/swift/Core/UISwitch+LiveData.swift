/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary

public extension UISwitch {
  func bindValue(liveData: LiveData<KotlinBoolean>) {
    setOn(isOn: liveData.value)
    liveData.addObserver { [weak self] isOn in
      self?.setOn(isOn: isOn)
    }
  }
  
  func bindValueTwoWay(liveData: MutableLiveData<KotlinBoolean>) {
    setOn(isOn: liveData.value)
    liveData.addObserver { [weak self] isOn in
      guard let view = self else { return }
      
      view.setOn(isOn: isOn)
    }
    
    let reversedObserver = LiveObserverValueSwitch(view: self, liveData: liveData)
    addTarget(reversedObserver, action: #selector(LiveObserverValueSwitch.valueDidChange(view:)), for: .valueChanged)
    addObserver(reversedObserver)
  }
  
  private func setOn(isOn: KotlinBoolean?) {
    let newValue = isOn?.boolValue ?? false
    if (self.isOn == newValue) { return }
    self.isOn = newValue
  }
}

extension UISwitch: HasLiveDataObservers { }

fileprivate class LiveObserverValueSwitch: NSObject {
  private weak var view: UISwitch?
  private weak var liveDataToWrite: MutableLiveData<KotlinBoolean>?
  
  init(view: UISwitch, liveData: LiveData<KotlinBoolean>) {
    self.view = view
    self.liveDataToWrite = liveData as? MutableLiveData<KotlinBoolean>
  }
  
  @objc
  func valueDidChange(view: UISwitch) {
    let value = view.isOn
    liveDataToWrite?.postValue(value: KotlinBoolean(bool: value))
  }
}
