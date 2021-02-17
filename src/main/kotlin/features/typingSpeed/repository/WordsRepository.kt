package features.typingSpeed.repository

import features.typingSpeed.db.WordsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.transactions.transaction

@Suppress("unused")
class WordsRepository(
    private val wordsDatabase: WordsDatabase
) {
    suspend fun getAllWords() = withContext(Dispatchers.IO) {
        val dao = wordsDatabase.getWordsDao()
        transaction {
            WordsDatabase.WordsDao.all()
                .map {
                    it.content
                }
        }
    }

    suspend fun saveWord(word: String) {
        val dao = wordsDatabase.getWordsDao()
        transaction {
            WordsDatabase.WordsDao.new {
                content = word
            }
        }
    }
}