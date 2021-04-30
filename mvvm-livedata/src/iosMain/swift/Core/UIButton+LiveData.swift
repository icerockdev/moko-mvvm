/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary

public extension UIButton {
  @available(*, deprecated, message: "use LiveData.bindBoolToControlEnabled and LiveData.bindBoolToViewBackgroundColor")
  @discardableResult func bindEnabled(
    liveData: LiveData<KotlinBoolean>,
    enabledColor: UIColor? = nil,
    disabledColor: UIColor? = nil
  ) -> Closeable {
    return UIButtonBindingKt.bindEnabled(
      self,
      liveData: liveData,
      enabledColor: enabledColor,
      disabledColor: disabledColor
    )
  }

  @available(*, deprecated, message: "use LiveData.bindStringToButtonTitle")
  @discardableResult func bindTitle(liveData: LiveData<NSString>) -> Closeable {
    return UIButtonBindingKt.bindTitle(
      self,
      liveData_: liveData
    )
  }

  @available(*, deprecated, message: "use LiveData.bindStringDescToButtonTitle")
  @discardableResult func bindTitle(liveData: LiveData<StringDesc>) -> Closeable {
    return UIButtonBindingKt.bindTitle(
      self,
      liveData: liveData
    )
  }

  @available(*, deprecated, message: "use LiveData.bindBoolToButtonImage")
  @discardableResult func bindImages(liveData: LiveData<KotlinBoolean>,
                  trueImage: UIImage,
                  falseImage: UIImage) -> Closeable {
    return UIButtonBindingKt.bindImages(
      self,
      liveData: liveData,
      trueImage: trueImage,
      falseImage: falseImage
    )
  }
}
