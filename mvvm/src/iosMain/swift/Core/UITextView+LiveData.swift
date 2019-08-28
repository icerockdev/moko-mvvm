/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary

public extension UITextView {
  func bindText(liveData: LiveData<StringDesc>) {
    setText(text: liveData.value)
    liveData.addObserver { [weak self] text in
      self?.setText(text: text)
    }
  }
  
  func bindText(liveData: LiveData<NSString>) {
    setText(text: liveData.value)
    liveData.addObserver { [weak self] text in
      self?.setText(text: text)
    }
  }
  
  private func setText(text: StringDesc?) {
    setText(text: text?.localized() as? NSString)
  }
  
  private func setText(text: NSString?) {
    let value: String = text as? String ?? ""
    
    if(self.text?.compare(value) == ComparisonResult.orderedSame) { return }
    
    self.text = value
  }
}
