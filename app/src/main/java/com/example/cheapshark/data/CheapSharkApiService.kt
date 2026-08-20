package com.example.cheapshark.data

import retrofit2.http.GET
import retrofit2.http.Query

interface CheapSharkApiService {

    /**
     * Busca promoções (deals) de jogos cujo título contenha [title].
     * Quando [title] é nulo/vazio, retorna as melhores promoções em geral.
     * Não requer nenhuma chave de API.
     */
    @GET("deals")
    suspend fun searchDeals(
        @Query("title") title: String?,
        @Query("pageSize") pageSize: Int = 30,
        @Query("sortBy") sortBy: String = "Deal Rating"
    ): List<Deal>

    /** Lista todas as lojas (para exibir o nome da loja de cada oferta). */
    @GET("stores")
    suspend fun getStores(): List<Store>
}
