package jp.matsuura.data.entity

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime
import org.joda.time.DateTime

data class Token(
    val email: String,
    val refreshToken: String,
    val createdAt: DateTime,
    val updatedAt: DateTime,
)

object Tokens : Table("token_table") {
    val email = varchar("email", 256)
    val refreshToken = varchar("refresh_token", 256)
    val createdAt = datetime("created_at").default(DateTime.now())
    val updatedAt = datetime("updated_at").default(DateTime.now())
    override val primaryKey = PrimaryKey(email)
}