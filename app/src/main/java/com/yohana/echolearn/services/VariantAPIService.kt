package com.yohana.echolearn.services

import com.yohana.echolearn.models.VariantListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface VariantAPIService {
    @GET("api/variants/{songId}/{type}")
    fun getVariants(
        @Path("songId") songId: Int,
        @Path("type") type: String
    ): Call<VariantListResponse>
}