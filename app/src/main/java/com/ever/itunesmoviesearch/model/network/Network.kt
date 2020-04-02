package com.ever.itunesmoviesearch.model.network

import com.ever.itunesmoviesearch.model.moviedata.ItunesResponse
import com.ever.itunesmoviesearch.model.network.endpointinterface.JsonPlaceHolder
import com.ever.itunesmoviesearch.model.network.helper.ServiceGenerator
import io.reactivex.Single

/**
 * Connect to internet using Retrofit
 */
class Network {
    companion object {
        val instance = Network()
    }

    private val placeHolderApi: JsonPlaceHolder = ServiceGenerator.createService(JsonPlaceHolder::class.java)

    /**
     * Search iTunes movie given specific term, country and media
     *
     * @return Response of iTunes to query
     */
    fun getMessagesRx(): Single<ItunesResponse> {
        return placeHolderApi.searchMovie("star", "au", "movie", "")
    }
}