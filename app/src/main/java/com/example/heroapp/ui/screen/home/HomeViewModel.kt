package com.example.heroapp.ui.screen.home

import androidx.lifecycle.ViewModel
import com.example.heroapp.data.ProgrammingLanguagesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.heroapp.model.ProgrammingLanguage

class HomeViewModel(private val repository: ProgrammingLanguagesRepository) : ViewModel() {
    private val _groupedProgrammingLanguages = MutableStateFlow(
        repository.getProgrammingLanguages()
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    )
    val groupedProgrammingLanguages: StateFlow<Map<Char, List<ProgrammingLanguage>>> get() = _groupedProgrammingLanguages

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query
    fun search(newQuery: String) {
        _query.value = newQuery
        _groupedProgrammingLanguages.value = repository.searchProgrammingLanguages(_query.value)
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    }
}



