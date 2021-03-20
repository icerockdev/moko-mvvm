/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary

public extension UITextView {
  @available(*, deprecated, message: "use LiveData.bindStringDescToTextViewText")
  @discardableResult func bindText(liveData: LiveData<StringDesc>) -> Closeable {
    return UITextViewBindingKt.bindText(
      self,
      liveData: liveData,
      formatter: nil
    )
  }

  @available(*, deprecated, message: "use LiveData.bindStringToTextViewText")
  @discardableResult func bindText(liveData: LiveData<NSString>) -> Closeable {
    return UITextViewBindingKt.bindText(
      self,
      liveData: liveData,
      formatter_: nil
    )
  }
}
