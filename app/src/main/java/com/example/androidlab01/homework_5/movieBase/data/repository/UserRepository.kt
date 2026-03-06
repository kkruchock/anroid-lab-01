package com.example.androidlab01.homework_5.movieBase.data.repository

import com.example.androidlab01.homework_5.movieBase.data.dao.UserDao
import com.example.androidlab01.homework_5.movieBase.data.entity.UserEntity
import com.example.androidlab01.homework_5.movieBase.domain.validator.StringProvider
import at.favre.lib.crypto.bcrypt.BCrypt

class UserRepository(
    private val userDao: UserDao,
    private val stringProvider: StringProvider
) {

    suspend fun register(login: String, email: String, password: String): Result<UserEntity> {
        //существует ли пользователь с таким логином
        if (userDao.getUserByLogin(login) != null) {
            return Result.failure(Exception(stringProvider.getUserExistsLoginError()))
        }

        //существует ли пользователь с email
        if (userDao.getUserByEmail(email) != null) {
            return Result.failure(Exception(stringProvider.getUserExistsEmailError()))
        }

        //хешируем
        val passwordHash = BCrypt.withDefaults().hashToString(12, password.toCharArray())

        val user = UserEntity(
            login = login,
            email = email,
            passwordHash = passwordHash
        )

        val userId = userDao.insert(user)
        return Result.success(user.copy(id = userId))
    }

    suspend fun login(login: String, password: String): Result<UserEntity> {
        val user = userDao.getUserByLogin(login)
            ?: return Result.failure(Exception(stringProvider.getUserNotFoundError()))

        //проверяем пароль
        val result = BCrypt.verifyer().verify(password.toCharArray(), user.passwordHash)

        return if (result.verified) {
            Result.success(user)
        } else {
            Result.failure(Exception(stringProvider.getWrongPasswordError()))
        }
    }

    suspend fun getUserById(id: Long): UserEntity? {
        return userDao.getUserById(id)
    }
}