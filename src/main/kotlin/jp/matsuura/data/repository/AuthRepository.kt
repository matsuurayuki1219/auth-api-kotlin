package jp.matsuura.data.repository

import jp.matsuura.data.entity.User

interface AuthRepository {
    fun findByEmail(email: String) : User?

}