/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary

public extension UIButton {
  func bindEnabled(liveData: LiveData<KotlinBoolean>,
                   enabledColor: UIColor? = nil,
                   disabledColor: UIColor? = nil) {
    UIButtonBindingKt.bindEnabled(
      self,
      liveData: liveData,
      enabledColor: enabledColor,
      disabledColor: disabledColor
    )
  }

  func bindTitle(liveData: LiveData<NSString>) {
    UIButtonBindingKt.bindTitle(
      self,
      liveData_: liveData
    )
  }

  func bindTitle(liveData: LiveData<StringDesc>) {
    UIButtonBindingKt.bindTitle(
      self,
      liveData: liveData
    )
  }

  func bindImages(liveData: LiveData<KotlinBoolean>,
                  trueImage: UIImage,
                  falseImage: UIImage) {
    UIButtonBindingKt.bindImages(
      self,
      liveData: liveData,
      trueImage: trueImage,
      falseImage: falseImage
    )
  }
}
