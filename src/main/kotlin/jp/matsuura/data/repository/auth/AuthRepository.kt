package jp.matsuura.data.repository.auth

import jp.matsuura.data.entity.User

interface AuthRepository {
    fun findByEmail(email: String) : User?

    fun insertUser(user: User)

}