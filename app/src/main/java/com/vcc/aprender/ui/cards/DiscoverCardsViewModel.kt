package com.vcc.aprender.ui.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vcc.aprender.data.cards.OfflineCardCatalog
import com.vcc.aprender.data.cards.CustomCardRepository
import com.vcc.aprender.data.progress.ProgressRepository
import com.vcc.aprender.domain.cards.CardDecks
import com.vcc.aprender.domain.cards.DeckFilter
import com.vcc.aprender.domain.cards.CardAnswer
import com.vcc.aprender.domain.cards.DiscoverCard
import com.vcc.aprender.domain.cards.DiscoverCardSession
import com.vcc.aprender.domain.cards.RevealTiles
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DiscoverCardsUiState(
    val card: DiscoverCard,
    val index: Int,
    val total: Int,
    val answer: CardAnswer? = null,
    val finished: Boolean = false,
    val tiles: RevealTiles = RevealTiles(),
    val error: String? = null,
    val loading: Boolean = false
)

class DiscoverCardsViewModel(
    private val profileId: Long,
    private val progressRepository: ProgressRepository,
    private val filter: DeckFilter = DeckFilter(),
    private val customCardRepository: CustomCardRepository? = null
) : ViewModel() {
    private var session = DiscoverCardSession(OfflineCardCatalog.cards)
    init {
        viewModelScope.launch {
            runCatching {
                val combined = OfflineCardCatalog.cards + (customCardRepository?.gameCards() ?: emptyList())
                CardDecks.build(combined, filter)?.cards
            }.onSuccess { cards ->
                if (cards.isNullOrEmpty()) {
                    _state.value = _state.value.copy(error = "Não existem cartões neste baralho.", loading = false)
                } else {
                    session = DiscoverCardSession(cards)
                    tiles = RevealTiles()
                    _state.value = snapshot()
                }
            }.onFailure {
                _state.value = _state.value.copy(error = "Não foi possível carregar os cartões.", loading = false)
            }
        }
    }
    private var tiles = RevealTiles()
    private val _state = MutableStateFlow(snapshot().copy(loading = true))
    val state = _state.asStateFlow()

    private fun snapshot(finished: Boolean = false, error: String? = null) = DiscoverCardsUiState(
        card = session.current, index = session.index, total = session.size,
        answer = session.answer, finished = finished, tiles = tiles, error = error
    )

    fun reveal(index: Int) {
        if (_state.value.loading || _state.value.finished || session.answer != null) return
        tiles = tiles.reveal(index)
        _state.value = snapshot()
    }

    fun choose(optionId: String) {
        if (_state.value.finished || session.answer != null) return
        val topic = session.current.topic
        if (session.current.options.none { it.id == optionId }) {
            _state.value = _state.value.copy(error = "Esta resposta não está disponível.")
            return
        }
        val result = session.submit(optionId)
        tiles = tiles.revealAll()
        _state.value = snapshot()
        viewModelScope.launch {
            runCatching {
                progressRepository.recordAnswer(profileId, topic, result.correct)
                if (result.correct) progressRepository.completeActivity(profileId, topic)
            }.onFailure { _state.value = _state.value.copy(error = "Não foi possível guardar o progresso.") }
        }
    }

    fun next() {
        if (_state.value.loading || session.answer == null) return
        if (session.next()) {
            tiles = RevealTiles()
            _state.value = snapshot()
        }
        else _state.value = snapshot(finished = true)
    }

    fun restart() {
        if (_state.value.loading) return
        session.reset()
        tiles = RevealTiles()
        _state.value = snapshot()
    }
}

class DiscoverCardsViewModelFactory(
    private val profileId: Long,
    private val progressRepository: ProgressRepository,
    private val filter: DeckFilter = DeckFilter(),
    private val customCardRepository: CustomCardRepository? = null
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(DiscoverCardsViewModel::class.java))
        return DiscoverCardsViewModel(profileId, progressRepository, filter, customCardRepository) as T
    }
}
