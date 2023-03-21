package jp.matsuura.data

import jp.matsuura.data.entity.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Schema
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class Migration {
    companion object {
        val database = Database.connect("jdbc:mysql://localhost:3306/auth_api_kotlin", driver = "com.mysql.cj.jdbc.Driver",
            user = "root", password = "root")

        val schema = Schema("demo").also {
            transaction {
                SchemaUtils.createSchema(it)
            }
        }

        val tables = arrayOf(
            Users
        ).also {
            transaction {
                SchemaUtils.create(*it)
            }
        }
    }
}