package com.ever.itunesmoviesearch.utility

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
 * @param None
 * @return current time
 */
fun getCurrentTime() : String {
    var date = Date()
    val formatter = SimpleDateFormat("YYYY MM dd HH:mm")
    return formatter.format(date)
}
