/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary

public extension UIView {
  func bindVisibility(liveData: LiveData<KotlinBoolean>, inverted: Bool = false) {
    setVisibility(visible: liveData.value, inverted: inverted)
    liveData.addObserver { [weak self] visible in
      self?.setVisibility(visible: visible, inverted: inverted)
    }
  }
  
  private func setVisibility(visible: KotlinBoolean?, inverted: Bool) {
    let isVisible = visible?.boolValue ?? false
    isHidden = inverted ? isVisible : !isVisible
  }
}
