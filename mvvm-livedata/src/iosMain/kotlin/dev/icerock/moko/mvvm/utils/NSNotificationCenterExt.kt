/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.utils

import dev.icerock.moko.kswift.KSwiftExclude
import dev.icerock.moko.mvvm.livedata.Closeable
import kotlinx.cinterop.ExportObjCClass
import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.cstr
import platform.Foundation.NSNotification
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSNotificationName
import platform.Foundation.NSSelectorFromString
import platform.darwin.NSObject
import platform.objc.OBJC_ASSOCIATION_RETAIN
import platform.objc.objc_setAssociatedObject

@KSwiftExclude
fun <T : Any> NSNotificationCenter.setEventHandler(
    notification: NSNotificationName,
    ref: T,
    lambda: T.() -> Unit
): Closeable {
    val lambdaTarget = NotificationLambdaTarget(lambda)

    addObserver(
        observer = lambdaTarget,
        selector = NSSelectorFromString("action:"),
        name = notification,
        `object` = ref
    )

    objc_setAssociatedObject(
        `object` = ref,
        key = "notification$notification".cstr,
        value = lambdaTarget,
        policy = OBJC_ASSOCIATION_RETAIN
    )

    return Closeable {
        removeObserver(lambdaTarget)
        // TODO remove associated object too, when it will be available in kotlin
    }
}

@ExportObjCClass
private class NotificationLambdaTarget<T>(
    val lambda: T.() -> Unit
) : NSObject() {

    @ObjCAction
    fun action(notification: NSNotification) {
        @Suppress("UNCHECKED_CAST")
        val ref = notification.`object` as T
        lambda(ref)
    }
}
