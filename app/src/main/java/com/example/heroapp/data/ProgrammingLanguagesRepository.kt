package com.example.heroapp.data

import com.example.heroapp.model.ProgrammingLanguage
import com.example.heroapp.model.ProgrammingLanguagesData

class ProgrammingLanguagesRepository {
    private val favoriteProgrammingLanguages = mutableListOf<String>()
    fun getProgrammingLanguages(): List<ProgrammingLanguage> {
        return ProgrammingLanguagesData.languages
    }

    fun searchProgrammingLanguages(query: String): List<ProgrammingLanguage> {
        return ProgrammingLanguagesData.languages.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    fun getProgrammingLanguageById(id: String): ProgrammingLanguage? {
        return ProgrammingLanguagesData.languages.find { it.id == id }
    }

    fun addToFavorites(heroId: String) {
        if (!favoriteProgrammingLanguages.contains(heroId)) {
            favoriteProgrammingLanguages.add(heroId)
        }
    }

    fun removeFromFavorites(heroId: String) {
        favoriteProgrammingLanguages.remove(heroId)
    }

    fun isFavorite(heroId: String): Boolean {
        return favoriteProgrammingLanguages.contains(heroId)
    }

    fun getFavoriteProgrammingLanguages(): List<ProgrammingLanguage> {
        return ProgrammingLanguagesData.languages.filter {
            favoriteProgrammingLanguages.contains(it.id)
        }
    }

    companion object {
        @Volatile
        private var instance: ProgrammingLanguagesRepository? = null
        fun getInstance(): ProgrammingLanguagesRepository =
            instance ?: synchronized(this) {
                ProgrammingLanguagesRepository().apply {
                    instance = this
                }
            }
    }
}
