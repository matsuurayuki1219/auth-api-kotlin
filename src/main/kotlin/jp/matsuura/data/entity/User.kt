package jp.matsuura.data.entity

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime
import org.joda.time.DateTime

data class User(
    val email: String,
    val password: String,
    val createdAt: DateTime,
    val updatedAt: DateTime,
)

object Users : Table("user_table") {
    val email = varchar("email", 256)
    val password = varchar("password", 256)
    val createdAt = datetime("created_at").default(DateTime.now())
    val updatedAt = datetime("updated_at").default(DateTime.now())
    override val primaryKey = PrimaryKey(email)
}