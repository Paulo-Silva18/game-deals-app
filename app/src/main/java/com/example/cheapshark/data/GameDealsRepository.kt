package com.example.cheapshark.data

/**
 * Camada de repositório. A CheapShark API não exige autenticação (sem apikey/hash),
 * então aqui só orquestramos duas chamadas:
 *  - /stores (uma vez, cacheada) para mapear storeID -> nome da loja
 *  - /deals para a busca em si
 */
class GameDealsRepository(
    private val apiService: CheapSharkApiService = RetrofitInstance.api
) {

    private var storesCache: Map<String, Store>? = null

    private suspend fun getStoresMap(): Map<String, Store> {
        return storesCache ?: apiService.getStores()
            .associateBy { it.storeID }
            .also { storesCache = it }
    }

    /** Retorna a lista de ofertas junto com o nome real da loja resolvido. */
    suspend fun searchDeals(query: String?): List<DealWithStore> {
        val stores = getStoresMap()
        val deals = apiService.searchDeals(title = query?.trim()?.ifBlank { null })
        return deals.map { deal ->
            DealWithStore(
                deal = deal,
                storeName = stores[deal.storeID]?.storeName ?: "Loja #${deal.storeID}"
            )
        }
    }
}

data class DealWithStore(
    val deal: Deal,
    val storeName: String
)
