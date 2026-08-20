package com.example.cheapshark.data

/**
 * Modelos de dados que espelham o JSON retornado pela CheapShark API.
 * Documentação: https://apidocs.cheapshark.com/
 *
 * Observação: a CheapShark retorna vários campos numéricos como String
 * (ex.: "9.99", "50"), então mapeamos como String e convertemos na UI
 * quando necessário.
 */

data class Deal(
    val dealID: String,
    val title: String,
    val storeID: String,
    val gameID: String,
    val salePrice: String,
    val normalPrice: String,
    val savings: String,
    val thumb: String?,
    val steamRatingText: String?,
    val steamRatingPercent: String?,
    val metacriticScore: String?,
    val releaseDate: Long?
) {
    // Percentual de desconto arredondado, ex: "63%"
    fun savingsPercent(): String {
        val value = savings.toDoubleOrNull() ?: return "--"
        return "${value.toInt()}%"
    }
}

data class Store(
    val storeID: String,
    val storeName: String,
    val isActive: Int,
    val images: StoreImages?
)

data class StoreImages(
    val banner: String?,
    val logo: String?,
    val icon: String?
)
