/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary

public extension UILabel {
  @available(*, deprecated, message: "use LiveData.bindStringToLabelText")
  @discardableResult func bindText(
    liveData: LiveData<NSString>,
    formatter: @escaping ((String) -> String) = { text in return text }
  ) -> Closeable {
    return UILabelBindingKt.bindText(
      self,
      liveData: liveData,
      formatter_: formatter
    )
  }

  @available(*, deprecated, message: "use LiveData.bindStringDescToLabelText")
  @discardableResult func bindText(
    liveData: LiveData<StringDesc>,
    formatter: @escaping ((String) -> String) = { text in return text }
  ) -> Closeable {
    return UILabelBindingKt.bindText(
      self,
      liveData: liveData,
      formatter: formatter
    )
  }
}
