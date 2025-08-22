package com.dsy2204.accesapp.connectivity

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberIsOnline(): State<Boolean> {
    val context = LocalContext.current
    val cm = remember { context.getSystemService(ConnectivityManager::class.java) }
    val online: MutableState<Boolean> = remember { mutableStateOf(isOnline(cm)) }

    DisposableEffect(cm) {
        val req = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        val cb = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) { online.value = isOnline(cm) }
            override fun onLost(network: Network) { online.value = isOnline(cm) }
        }
        cm.registerNetworkCallback(req, cb)
        onDispose { cm.unregisterNetworkCallback(cb) }
    }
    return online
}

private fun isOnline(cm: ConnectivityManager): Boolean {
    val net = cm.activeNetwork ?: return false
    val caps = cm.getNetworkCapabilities(net) ?: return false
    return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

