package com.ever.itunesmoviesearch.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.text.SimpleDateFormat
import java.util.*

/**
 * Contains utility methods for the application
 *
 */

/**
 * Clear and dispose all the contained Disposables
 *
 * @param bag composite disposable
 * @return None
 */
fun Disposable.disposedBy(bag: CompositeDisposable) {
    bag.add(this)
}

/**
 * Obtains current time in YYYY MM dd HH:mm format
 *
 * @return current time
 */
fun getCurrentTime() : String {
    val date = Date()
    val formatter = SimpleDateFormat("YYYY MM dd HH:mm")
    return formatter.format(date)
}

/**
 * Obtains network connection availability
 *
 * @return true - If device is connected to internet
 *         false - If device is not connected to internet
 */
fun isNetworkAvailable (context: Context) : Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    return activeNetwork?.isConnectedOrConnecting == true
}
