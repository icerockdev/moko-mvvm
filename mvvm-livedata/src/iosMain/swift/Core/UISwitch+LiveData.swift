/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary

public extension UISwitch {
  func bindValue(liveData: LiveData<KotlinBoolean>) {
    UISwitchBindingKt.bindValue(
      self,
      liveData: liveData
    )
  }

  func bindValueTwoWay(liveData: MutableLiveData<KotlinBoolean>) {
    UISwitchBindingKt.bindValueTwoWay(
      self,
      liveData: liveData
    )
  }
}
