package com.ohayo.app.core.data.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest.Builder
import androidx.core.content.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import javax.inject.Inject

/**
 * Created by Furuichi on 28/12/2022
 */
class ConnectivityManagerNetworkMonitor @Inject constructor(
    @ApplicationContext private val context: Context
) : NetworkMonitor {
    override val isOnline: Flow<Boolean> = callbackFlow {
        val connectivityManager = context.getSystemService<ConnectivityManager>()

        val callback = object : NetworkCallback() {
            override fun onAvailable(network: Network) {
                channel.trySend(connectivityManager.isCurrentlyConnected())
            }

            override fun onLost(network: Network) {
                channel.trySend(connectivityManager.isCurrentlyConnected())
            }

            override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
                channel.trySend(connectivityManager.isCurrentlyConnected())
            }
        }

        connectivityManager?.registerNetworkCallback(
            Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build(),
            callback
        )

        awaitClose {
            connectivityManager?.unregisterNetworkCallback(callback)
        }
    }
        .conflate()

    private fun ConnectivityManager?.isCurrentlyConnected() = when (this) {
        null -> false
        else -> activeNetwork
            ?.let(::getNetworkCapabilities)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }
}