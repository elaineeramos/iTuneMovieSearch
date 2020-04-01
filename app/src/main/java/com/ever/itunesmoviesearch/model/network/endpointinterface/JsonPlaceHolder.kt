package com.ever.itunesmoviesearch.model.network.endpointinterface

import com.ever.itunesmoviesearch.model.moviedata.ItunesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * JSON placeholder for iTunes API query
 */
interface JsonPlaceHolder {

    /**
     * Queries for the movies in iTunes with specific term
     *
     * @return Response from iTunes
     */
    @GET("/search")
    fun searchMovie(@Query("term") term: String,
                    @Query("country") country: String,
                    @Query("media") media: String,
                    @Query("all")  all: String): Single<ItunesResponse>
}