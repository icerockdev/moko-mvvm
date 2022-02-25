/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

expect open class MutableLiveData<T>(initialValue: T) : LiveData<T> {
    override var value: T
}
