package jp.matsuura.data.repository

import jp.matsuura.data.entity.Token
import jp.matsuura.data.entity.User

interface AuthRepository {
    fun findByEmail(email: String) : User?

    fun insertUser(user: User)

    fun insertToken(token: Token)

}