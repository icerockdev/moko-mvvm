/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary

public extension UITextView {
  func bindText(liveData: LiveData<StringDesc>) {
    UITextViewBindingKt.bindText(
      self,
      liveData: liveData,
      formatter__: nil
    )
  }

  func bindText(liveData: LiveData<NSString>) {
    UITextViewBindingKt.bindText(
      self,
      liveData: liveData,
      formatter___: nil
    )
  }
}
