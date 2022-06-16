/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm

import dev.icerock.moko.mvvm.state.ResourceState

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("ResourceState", "dev.icerock.moko.mvvm.state"),
    level = DeprecationLevel.WARNING
)
typealias ResourceState<TData, TError> = ResourceState<TData, TError>
