/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.flow.binding

import dev.icerock.moko.kswift.KSwiftExclude
import dev.icerock.moko.mvvm.flow.DisposableHandle
import kotlinx.cinterop.ExportObjCClass
import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.cstr
import platform.Foundation.NSSelectorFromString
import platform.UIKit.UIControl
import platform.UIKit.UIControlEvents
import platform.darwin.NSObject
import platform.objc.OBJC_ASSOCIATION_RETAIN
import platform.objc.objc_setAssociatedObject

@KSwiftExclude
fun <T : UIControl> T.setEventHandler(
    event: UIControlEvents,
    lambda: T.() -> Unit
): DisposableHandle {
    val lambdaTarget = ControlLambdaTarget(lambda)
    val action = NSSelectorFromString("action:")

    addTarget(
        target = lambdaTarget,
        action = action,
        forControlEvents = event
    )

    objc_setAssociatedObject(
        `object` = this,
        key = "event$event".cstr,
        value = lambdaTarget,
        policy = OBJC_ASSOCIATION_RETAIN
    )

    return DisposableHandle {
        removeTarget(target = lambdaTarget, action = action, forControlEvents = event)
        // TODO remove associated object too, when it will be available in kotlin
    }
}

@ExportObjCClass
private class ControlLambdaTarget<T : UIControl>(
    private val lambda: T.() -> Unit
) : NSObject() {

    @ObjCAction
    fun action(sender: UIControl) {
        @Suppress("UNCHECKED_CAST")
        lambda(sender as T)
    }
}
