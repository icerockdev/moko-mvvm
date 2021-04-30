/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary

public extension UISwitch {
  @available(*, deprecated, message: "use LiveData.bindBoolToSwitchOn")
  @discardableResult func bindValue(liveData: LiveData<KotlinBoolean>) -> Closeable {
    return UISwitchBindingKt.bindValue(
      self,
      liveData: liveData
    )
  }

  @available(*, deprecated, message: "use MutableLiveData.bindBoolTwoWayToSwitchOn")
  @discardableResult func bindValueTwoWay(liveData: MutableLiveData<KotlinBoolean>) -> Closeable {
    return UISwitchBindingKt.bindValueTwoWay(
      self,
      liveData: liveData
    )
  }
}
