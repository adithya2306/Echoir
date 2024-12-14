package dev.jyotiraditya.echoir.presentation.screens.search

import dev.jyotiraditya.echoir.R
import dev.jyotiraditya.echoir.domain.model.SearchResult

data class SearchState(
    val query: String = "",
    val searchType: SearchType = SearchType.TRACKS,
    val searchFilter: SearchFilter = SearchFilter(),
    val results: List<SearchResult> = emptyList(),
    val filteredResults: List<SearchResult> = emptyList(),
    val status: SearchStatus = SearchStatus.Empty,
    val error: String? = null
)

enum class SearchType(val title: Int) {
    TRACKS(R.string.tracks),
    ALBUMS(R.string.albums)
}

data class SearchFilter(
    val qualities: MutableList<SearchQuality> = mutableListOf(),
    var contentFilters: MutableList<SearchContentFilter> = mutableListOf()
)

enum class SearchContentFilter(
    val label: Int,
    val explicit: Boolean
) {
    CLEAN(R.string.clean, false),
    EXPLICIT(R.string.explicit, true),
}

enum class SearchQuality(
    val label: Int,
    val format: String
) {
    HIRES(R.string.hires_label, "HIRES_LOSSLESS"),
    LOSSLESS(R.string.lossless_label, "LOSSLESS"),
    ATMOS(R.string.dolby_atmos, "DOLBY_ATMOS"),
}

sealed class SearchStatus {
    data object Empty : SearchStatus()
    data object Ready : SearchStatus()
    data object Loading : SearchStatus()
    data object Success : SearchStatus()
    data object NoResults : SearchStatus()
    data object Error : SearchStatus()
}