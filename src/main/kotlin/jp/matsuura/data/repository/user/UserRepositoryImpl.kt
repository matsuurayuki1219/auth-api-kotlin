package jp.matsuura.data.repository.user

import jp.matsuura.data.entity.User
import jp.matsuura.data.entity.Users
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepositoryImpl: UserRepository {

    override fun findByEmail(email: String): User? {
        return transaction {
            Users.select { Users.email eq email}.singleOrNull()?.toUser()
        }
    }

    private fun ResultRow.toUser(): User {
        return User(
            email = this[Users.email],
            password = this[Users.password],
            createdAt = this[Users.createdAt],
            updatedAt = this[Users.updatedAt],
        )
    }

}