package com.example.heroapp.di

import com.example.heroapp.data.ProgrammingLanguagesRepository

object Injection {
    fun provideRepository():  ProgrammingLanguagesRepository{
        return ProgrammingLanguagesRepository.getInstance()
    }
}