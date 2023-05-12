package com.vn.wecare.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun checkInternetConnection(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val network = connectivityManager.activeNetwork ?: return false

    // Representation of the capabilities of an active network.
    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

    return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        // else return false
        else -> false
    }
}

//class NetWorkStatusTracker(context: Context) {
//    private val connectivityManager =
//        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//
//    val networkStatus = callbackFlow<NetworkStatus> {
//        val networkStatusCallback = object : ConnectivityManager.NetworkCallback() {
//            override fun onUnavailable() {
//                trySend(NetworkStatus.NOT_REACHABLE)
//            }
//
//            override fun onAvailable(network: Network) {
//                trySend(NetworkStatus.REACHABLE_VIA_WI_FI)
//            }
//
//            override fun onLost(network: Network) {
//                offer(NetworkStatus.Unavailable)
//            }
//        }
//
//        val request = NetworkRequest.Builder()
//            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
//            .build()
//        connectivityManager.registerNetworkCallback(request, networkStatusCallback)
//
//        awaitClose {
//            connectivityManager.unregisterNetworkCallback(networkStatusCallback)
//        }
//    }
//}