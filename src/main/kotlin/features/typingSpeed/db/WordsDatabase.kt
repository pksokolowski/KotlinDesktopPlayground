package features.typingSpeed.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.concurrent.atomic.AtomicBoolean

@Suppress("MemberVisibilityCanBePrivate")
class WordsDatabase {
    private val isInitializedOrInitializing = AtomicBoolean(false)

    suspend fun initialize() = withContext(Dispatchers.IO) scope@{
        if (isInitializedOrInitializing.getAndSet(true)) return@scope

        Database.connect("jdbc:sqlite:data.db", "org.sqlite.JDBC")
        transaction {
            //addLogger(StdOutSqlLogger)
            SchemaUtils.create(Words)
        }
    }

    suspend fun getWordsDao() = withContext(Dispatchers.IO) {
        initialize()
        dao
    }

    private val dao = object : IntEntityClass<WordsDao>(Words) {}

    private object Words : IntIdTable() {
        val content = varchar("word", 50)
    }

    class WordsDao(id: EntityID<Int>) : IntEntity(id) {
        companion object : IntEntityClass<WordsDao>(Words)

        var content by Words.content
    }

}