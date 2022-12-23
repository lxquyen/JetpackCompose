package com.ohayo.app.core.data.util

import kotlinx.coroutines.flow.Flow

/**
 * Created by Furuichi on 28/12/2022
 */
interface NetworkMonitor {
    val isOnline: Flow<Boolean>
}