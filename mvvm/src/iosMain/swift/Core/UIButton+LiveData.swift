/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary
import MultiPlatformLibraryResources

public extension UIButton {
  func bindEnabled(liveData: LiveData<KotlinBoolean>,
                   enabledColor: UIColor? = nil,
                   disabledColor: UIColor? = nil) {
    setEnabled(
      enabled: liveData.value,
      enabledColor: enabledColor,
      disabledColor: disabledColor
    )
    
    liveData.addObserver { [weak self] enabled in
      self?.setEnabled(
        enabled: enabled,
        enabledColor: enabledColor,
        disabledColor: disabledColor
      )
    }
  }
  
  private func setEnabled(enabled: KotlinBoolean?,
                          enabledColor: UIColor?,
                          disabledColor: UIColor?) {
    isEnabled = enabled?.boolValue ?? false
    if let color = isEnabled ? enabledColor : disabledColor {
      self.backgroundColor = color
    }
  }
  
  func bindTitle(liveData: LiveData<NSString>) {
    setTitle(title: liveData.value)
    liveData.addObserver { [weak self] title in
      self?.setTitle(title: title)
    }
  }
  
  func bindTitle(liveData: LiveData<StringDesc>) {
    setTitle(title: liveData.value)
    liveData.addObserver { [weak self] title in
      self?.setTitle(title: title)
    }
  }
  
  private func setTitle(title: StringDesc?) {
    setTitle(title: title?.localized() as? NSString)
  }
  
  private func setTitle(title: NSString?) {
    if let title = title as? String {
      setTitle(title, for: .normal)
    }
  }
  
  func bindImages(liveData: LiveData<KotlinBoolean>,
                  trueImage: UIImage,
                  falseImage: UIImage) {
    setImages(
      enabled: liveData.value,
      trueImage: trueImage,
      falseImage: falseImage
    )
    liveData.addObserver { [weak self] enabled in
      self?.setImages(
        enabled: enabled,
        trueImage: trueImage,
        falseImage: falseImage
      )
    }
  }
  
  private func setImages(enabled: KotlinBoolean?,
                         trueImage: UIImage,
                         falseImage: UIImage) {
    setImage((enabled?.boolValue ?? false) ? trueImage : falseImage, for: .normal)
  }
}
