package com.example.cheapshark.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cheapshark.data.DealWithStore
import com.example.cheapshark.data.GameDealsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface DealsUiState {
    data object Loading : DealsUiState
    data class Success(val deals: List<DealWithStore>) : DealsUiState
    data class Error(val message: String) : DealsUiState
}

class GameDealsViewModel(
    private val repository: GameDealsRepository = GameDealsRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<DealsUiState>(DealsUiState.Loading)
    val uiState: StateFlow<DealsUiState> = _uiState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private var searchJob: Job? = null

    init {
        // Carrega as melhores promoções assim que o app abre
        search("")
    }

    fun onQueryChanged(newQuery: String) {
        _query.update { newQuery }
        // Busca dinâmica: aguarda uma pequena pausa após o usuário parar de digitar
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            search(newQuery)
        }
    }

    fun retry() {
        search(_query.value)
    }

    private fun search(term: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _uiState.update { DealsUiState.Loading }
            try {
                val results = repository.searchDeals(term)
                _uiState.update { DealsUiState.Success(results) }
            } catch (e: Exception) {
                _uiState.update {
                    DealsUiState.Error(e.message ?: "Erro desconhecido ao buscar promoções.")
                }
            }
        }
    }
}
