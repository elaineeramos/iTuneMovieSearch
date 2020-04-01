package com.ever.itunesmoviesearch.model.network

import android.content.res.Resources
import com.ever.itunesmoviesearch.R

/**
 * Obtain term parameter for iTunes query
 *
 * @return term parameter for query
 */
fun getSearchTerm() : String {
    return Resources.getSystem().getString(R.string.search_term)
}

/**
 * Obtain country parameter for iTunes query
 *
 * @return country parameter for query
 */
fun getSearchCountry() : String {
    return Resources.getSystem().getString(R.string.search_country)
}

/**
 * Obtain media parameter for iTunes query
 *
 * @return media parameter for query
 */
fun getSearchMedia() : String {
    return Resources.getSystem().getString(R.string.search_media)
}