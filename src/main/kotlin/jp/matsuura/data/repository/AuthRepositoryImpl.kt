package jp.matsuura.data.repository

import jp.matsuura.data.entity.Token
import jp.matsuura.data.entity.Tokens
import jp.matsuura.data.entity.User
import jp.matsuura.data.entity.Users
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class AuthRepositoryImpl : AuthRepository {

    override fun findByEmail(email: String): User? {
        return transaction {
            Users.select { Users.email eq email}.singleOrNull()?.toUser()
        }
    }

    override fun insertUser(user: User) {
        transaction {
            Users.insert {
                it[email] = user.email
                it[password] = user.password
                it[createdAt] = user.createdAt
                it[updatedAt] = user.updatedAt
            }
        }
    }

    override fun insertToken(token: Token) {
        transaction {
            Tokens.insert {
                it[email] = token.email
                it[refreshToken] = token.refreshToken
                it[createdAt] = token.createdAt
                it[updatedAt] = token.updatedAt
            }
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