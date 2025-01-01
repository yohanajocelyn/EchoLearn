package com.yohana.echolearn.repositories

import com.yohana.echolearn.models.VariantListResponse
import com.yohana.echolearn.services.VariantAPIService
import retrofit2.Call

interface VariantRepository{
    fun getVariants(songId: Int, type: String): Call<VariantListResponse>
}

class NetworkVariantRepository(
    private val variantAPIService: VariantAPIService
): VariantRepository{
    override fun getVariants(songId: Int, type: String): Call<VariantListResponse> {
        return variantAPIService.getVariants(songId, type)
    }
}