/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary

public extension UITextField {
  @available(*, deprecated, message: "use LiveData.bindStringToTextFieldText")
  @discardableResult func bindText(
    liveData: LiveData<NSString>,
    formatter: ((String) -> String)? = nil
  ) -> Closeable {
    return UITextFieldBindingKt.bindText(
      self,
      liveData: liveData,
      formatter_: formatter
    )
  }

  @available(*, deprecated, message: "use LiveData.bindStringDescToTextFieldText")
  @discardableResult func bindText(
    liveData: LiveData<StringDesc>,
    formatter: ((String) -> String)? = nil
  ) -> Closeable {
    return UITextFieldBindingKt.bindText(
      self,
      liveData: liveData,
      formatter: formatter
    )
  }

  @available(*, deprecated, message: "use LiveData.bindStringTwoWayToTextFieldText")
  @discardableResult func bindTextTwoWay(
    liveData: MutableLiveData<NSString>,
    formatter: ((String) -> String)? = nil,
    reverseFormatter:((String) -> String)? = nil
  ) -> Closeable {
    return UITextFieldBindingKt.bindTextTwoWay(
      self,
      liveData: liveData,
      formatter: formatter,
      reverseFormatter: reverseFormatter
    )
  }

  @available(*, deprecated, message: "use LiveData.bindBoolToViewFocus")
  @discardableResult func bindFocus(liveData: LiveData<KotlinBoolean>) -> Closeable {
    return UITextFieldBindingKt.bindFocus(
      self,
      liveData: liveData
    )
  }

  @available(*, deprecated, message: "use MutableLiveData.bindBoolTwoWayToControlFocus")
  @discardableResult func bindFocus(liveData: MutableLiveData<KotlinBoolean>) -> Closeable {
    return UITextFieldBindingKt.bindFocusTwoWay(
      self,
      liveData: liveData
    )
  }
}
