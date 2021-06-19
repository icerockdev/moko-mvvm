/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary
import AlamofireImage

public extension UIImageView {
  public func bindImageUrl(
    liveData: LiveData<NSString>,
    defaultImage: UIImage? = nil
  ) {
    liveData.bind(view: self) { (imageView, imageUrl) in
      guard let imageView = imageView as? UIImageView else {
        return
      }
      imageView.setImageFromUrlString(
        urlString: imageUrl as? String,
        defaultImage: defaultImage,
        completion: nil
      )
    }
  }

  fileprivate func setImageFromUrlString(
    urlString: String?,
    defaultImage: UIImage? = nil,
    completion: (() -> Void)? = nil
  ) {
    guard let urlString = urlString else {
      image = defaultImage
      completion?()
      return
    }
    guard let url = URL(string: urlString) else {
      print("Can't parse url: \(urlString)")
      image = defaultImage
      completion?()
      return
    }
    
    af_setImage(
      withURL: url,
      placeholderImage: defaultImage,
      filter: nil,
      progress: nil,
      progressQueue: DispatchQueue.main,
      imageTransition: .noTransition,
      runImageTransitionIfCached: false
    ) { _ in
      completion?()
    }
  }
}
