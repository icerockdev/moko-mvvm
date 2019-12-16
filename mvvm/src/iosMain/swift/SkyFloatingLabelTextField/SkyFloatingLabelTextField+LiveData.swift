/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary
import SkyFloatingLabelTextField

public extension SkyFloatingLabelTextField {
  func bindError(liveData: LiveData<NSString>) {
    setError(text: liveData.value)
    liveData.addObserver { [weak self] text in
      self?.setError(text: text)
    }
  }
  
  func bindError(liveData: LiveData<StringDesc>) {
    setError(text: liveData.value)
    liveData.addObserver { [weak self] text in
      self?.setError(text: text)
    }
  }
  
  private func setError(text: StringDesc?) {
    setError(text: text?.localized() as? NSString)
  }
  
  private func setError(text: NSString?) {
    let stringValue = text as? String ?? ""
    
    if(self.errorMessage?.compare(stringValue) == ComparisonResult.orderedSame) { return }
    
    errorMessage = stringValue
  }
}
