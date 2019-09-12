//
//  AlertViewControllerExt.swift
//  TestProj
//
//  Created by Aleksey Mikhailov on 12/09/2019.
//  Copyright © 2019 IceRock Development. All rights reserved.
//

import UIKit

extension UIViewController {
    func showAlert(text: String) {
        let alert = UIAlertController(title: nil,
                                      message: text,
                                      preferredStyle: .alert)
        
        alert.addAction(UIAlertAction(title: "Close",
                                      style: .default,
                                      handler: nil))
        
        self.present(alert, animated: true, completion: nil)
    }
}
