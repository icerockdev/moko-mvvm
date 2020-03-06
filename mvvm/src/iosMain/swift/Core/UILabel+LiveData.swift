/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary

public extension UILabel {
  func bindText(
    liveData: LiveData<NSString>,
    formatter: @escaping ((String) -> String) = { text in return text }
  ) {
    UILabelBindingKt.bindText(
      self,
      liveData: liveData,
      formatter_____: formatter
    )
  }

  func bindText(
    liveData: LiveData<StringDesc>,
    formatter: @escaping ((String) -> String) = { text in return text }
  ) {
    UILabelBindingKt.bindText(
      self,
      liveData: liveData,
      formatter____: formatter
    )
  }
}
