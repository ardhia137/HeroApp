package com.example.heroapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heroapp.data.ProgrammingLanguagesRepository

import com.example.heroapp.model.ProgrammingLanguage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: ProgrammingLanguagesRepository) : ViewModel() {

    private val _programmingLanguage = MutableStateFlow<ProgrammingLanguage?>(null)
    val programmingLanguage: StateFlow<ProgrammingLanguage?> = _programmingLanguage

    fun fetchProgrammingLanguage(id: String) {
        viewModelScope.launch {
            _programmingLanguage.value = repository.getProgrammingLanguageById(id)
        }
    }

}
