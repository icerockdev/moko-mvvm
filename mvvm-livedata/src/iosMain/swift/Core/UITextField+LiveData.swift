/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary

public extension UITextField {
  func bindText(
    liveData: LiveData<NSString>,
    formatter: ((String) -> String)? = nil
  ) {
    UITextFieldBindingKt.bindText(
      self,
      liveData: liveData,
      formatter_: formatter
    )
  }

  func bindText(
    liveData: LiveData<StringDesc>,
    formatter: ((String) -> String)? = nil
  ) {
    UITextFieldBindingKt.bindText(
      self,
      liveData: liveData,
      formatter: formatter
    )
  }

  func bindTextTwoWay(
    liveData: MutableLiveData<NSString>,
    formatter: ((String) -> String)? = nil,
    reverseFormatter:((String) -> String)? = nil
  ) {
    UITextFieldBindingKt.bindTextTwoWay(
      self,
      liveData: liveData,
      formatter: formatter,
      reverseFormatter: reverseFormatter
    )
  }

  func bindFocus(liveData: LiveData<KotlinBoolean>) {
    UITextFieldBindingKt.bindFocus(
      self,
      liveData: liveData
    )
  }

  func bindFocus(liveData: MutableLiveData<KotlinBoolean>) {
    UITextFieldBindingKt.bindFocusTwoWay(
      self,
      liveData: liveData
    )
  }
}
