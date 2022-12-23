package com.ohayo.app.core.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

/**
 * Created by Furuichi on 29/12/2022
 */

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val dispatcher: DispatcherType)

enum class DispatcherType {
    IO,
    Main,
    MainImmediate,
    Default
}