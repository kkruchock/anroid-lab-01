package com.example.androidlab01.homework_5.movieBase.di

import android.content.Context
import com.example.androidlab01.homework_5.movieBase.data.database.MovieDatabase
import com.example.androidlab01.homework_5.movieBase.data.repository.MovieRepository
import com.example.androidlab01.homework_5.movieBase.data.repository.UserRepository
import com.example.androidlab01.homework_5.movieBase.data.session.SessionManager
import com.example.androidlab01.homework_5.movieBase.domain.validator.StringProvider
import com.example.androidlab01.homework_5.movieBase.domain.validator.Validators

//зависимости
class AppContainer(context: Context) {

    private val database: MovieDatabase = MovieDatabase.getInstance(context)

    val sessionManager: SessionManager = SessionManager(context)

    val stringProvider: StringProvider = StringProvider(context)

    val validators: Validators = Validators(stringProvider)

    val userRepository: UserRepository = UserRepository(database.userDao(), stringProvider)

    val movieRepository: MovieRepository = MovieRepository(database.movieDao())
}

