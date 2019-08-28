/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary

public extension UILabel {
  func bindText(liveData: LiveData<NSString>, formatter: @escaping ((String?) -> String?) = { text in return text }) {
    setText(
      text: liveData.value,
      formatter: formatter
    )
    
    liveData.addObserver { [weak self] string in
      self?.setText(
        text: string,
        formatter: formatter
      )
    }
  }
  
  func bindText(liveData: LiveData<StringDesc>, formatter: @escaping ((String?) -> String?) = { text in return text }) {
    setText(
      text: liveData.value,
      formatter: formatter
    )
    
    liveData.addObserver { [weak self] string in
      self?.setText(
        text: string,
        formatter: formatter
      )
    }
  }
  
  private func setText(text: StringDesc?,
                       formatter: @escaping ((String?) -> String?)) {
    setText(
      text: text?.localized() as? NSString,
      formatter: formatter
    )
  }
  
  private func setText(text: NSString?,
                       formatter: @escaping ((String?) -> String?)) {
    let value: String = text as? String ?? ""
    let formattedValue = formatter(value) ?? ""
    
    if(self.text?.compare(formattedValue) == ComparisonResult.orderedSame) { return }
    
    self.text = formattedValue
  }
}
