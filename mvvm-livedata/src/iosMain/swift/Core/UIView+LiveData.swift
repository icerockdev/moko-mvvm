/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary

public extension UIView {
  @available(*, deprecated, message: "use LiveData.bindBoolToViewHidden")
  @discardableResult func bindVisibility(
    liveData: LiveData<KotlinBoolean>,
    inverted: Bool = false
  ) -> Closeable {
    return UIViewBindingKt.bindVisibility(
      self,
      liveData: liveData,
      inverted: inverted
    )
  }
}
