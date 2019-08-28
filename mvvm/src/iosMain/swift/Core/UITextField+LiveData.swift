/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary

public extension UITextField {
  func bindText(liveData: LiveData<NSString>, formatter: ((String) -> String)? = nil) {
    setText(text: liveData.value, formatter: formatter)
    liveData.addObserver { [weak self] text in
      self?.setText(text: liveData.value, formatter: formatter)
    }
  }
  
  func bindText(liveData: LiveData<StringDesc>, formatter: ((String) -> String)? = nil) {
    setText(text: liveData.value, formatter: formatter)
    liveData.addObserver { [weak self] text in
      self?.setText(text: liveData.value, formatter: formatter)
    }
  }
  
  private func setText(text: StringDesc?,
                       formatter: ((String) -> String)? = nil) {
    setText(text: text?.localized() as? NSString,
            formatter: formatter)
  }
  
  private func setText(text: NSString?,
                       formatter: ((String) -> String)? = nil) {
    let newValue: String = text as? String ?? ""
    let formattedValue: String
    if let formatter = formatter {
      formattedValue = formatter(newValue)
    } else {
      formattedValue = newValue
    }
    
    if(self.text?.compare(formattedValue) == ComparisonResult.orderedSame) { return }
    
    self.text = formattedValue
  }
  
  func bindTextTwoWay(liveData: MutableLiveData<NSString>,
                      formatter: ((String) -> String)? = nil,
                      reverseFormatter:((String) -> String)? = nil) {
    setText(text: liveData.value, formatter: formatter)
    liveData.addObserver { [weak self] text in
      self?.setText(text: liveData.value, formatter: formatter)
    }
    
    let observer = LiveObserverTextTextField(view: self,
                                             reverseFormatter: reverseFormatter,
                                             liveData: liveData)
    addTarget(observer,
              action: #selector(observer.textFieldDidChange(_:)),
              for: UIControl.Event.editingChanged)
    addObserver(observer)
  }
  
  func bindFocus(liveData: LiveData<KotlinBoolean>) {
    setFocus(focused: liveData.value)
    liveData.addObserver { [weak self] focused in
      self?.setFocus(focused: focused)
    }
  }
  
  func bindFocus(liveData: MutableLiveData<KotlinBoolean>) {
    setFocus(focused: liveData.value)
    liveData.addObserver { [weak self] focused in
      self?.setFocus(focused: focused)
    }
    
    let observer = LiveObserverFocusTextField(
      view: self,
      liveData: liveData
    )
    
    addTarget(observer,
              action: #selector(observer.textFieldDidFocus(_:)),
              for: UIControl.Event.editingDidBegin)
    addTarget(observer,
              action: #selector(observer.textFieldDidUnfocus(_:)),
              for: UIControl.Event.editingDidEndOnExit)
    addTarget(observer,
              action: #selector(observer.textFieldDidUnfocus(_:)),
              for: UIControl.Event.editingDidEnd)
    
    addObserver(observer)
  }
  
  private func setFocus(focused: KotlinBoolean?) {
    guard let value = focused?.boolValue else { return }
    
    if value {
      becomeFirstResponder()
    } else {
      if let next = next, next.canBecomeFirstResponder {
        next.becomeFirstResponder()
      } else {
        resignFirstResponder()
      }
    }
  }
}

extension UITextField: HasLiveDataObservers { }

fileprivate class LiveObserverFocusTextField: NSObject {
  private weak var view: UITextField?
  private weak var liveDataToWrite: MutableLiveData<KotlinBoolean>?
  
  init(view: UITextField, liveData: MutableLiveData<KotlinBoolean>) {
    self.view = view
    self.liveDataToWrite = liveData
  }
  
  @objc
  func textFieldDidFocus(_ textField: UITextField) {
    liveDataToWrite?.value = true
  }
  
  @objc
  func textFieldDidUnfocus(_ textField: UITextField) {
    liveDataToWrite?.value = false
  }
}

fileprivate class LiveObserverTextTextField: NSObject {
  
  private weak var view: UITextField?
  private weak var liveDataToWrite: MutableLiveData<NSString>?
  private let reverseFormatter: ((String) -> String)?
  
  init(view: UITextField,
       reverseFormatter:((String) -> String)?,
       liveData: MutableLiveData<NSString>) {
    self.view = view
    self.reverseFormatter = reverseFormatter
    self.liveDataToWrite = liveData
  }
  
  @objc
  func textFieldDidChange(_ textField: UITextField) {
    let valueToPost: String
    if let reverseFormatter = reverseFormatter {
      valueToPost = reverseFormatter(textField.text ?? "")
    } else {
      valueToPost = textField.text ?? ""
    }
    
    if (liveDataToWrite?.value as? String == valueToPost) { return }
    
    liveDataToWrite?.postValue(value: NSString(string: valueToPost))
  }
}
