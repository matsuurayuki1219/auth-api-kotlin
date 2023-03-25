package jp.matsuura.data.repository.user

import jp.matsuura.data.entity.User

interface UserRepository {

    fun findByEmail(email: String): User?

}