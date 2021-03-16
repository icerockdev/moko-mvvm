/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary
import SkyFloatingLabelTextField

public extension SkyFloatingLabelTextField {
  func bindError(liveData: LiveData<NSString>) {
    liveData.bind(view: self) { (field, text) in
      guard let field = field as? SkyFloatingLabelTextField,
            let text = text as? String else {
        return
      }
      
      field.errorMessage = text
    }
  }
  
  func bindError(liveData: LiveData<StringDesc>) {
    liveData.bind(view: self) { (field, text) in
      guard let field = field as? SkyFloatingLabelTextField,
            let text = text as? StringDesc else {
        return
      }
      
      field.errorMessage = text.localized()
    }
  }
}
