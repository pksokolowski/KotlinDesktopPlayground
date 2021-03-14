package features.coroutines.api

import kotlinx.coroutines.delay
import java.lang.RuntimeException

/**
 * Provides some fake data which might be useful across samples. Thi
 */
class FakeQuotationsApi {
    data class User(val id: Long, val name: String)
    data class Quote(val authorId: Long, val content: String)

    /**
     *  id for which the fake api is to throw an exception when [getQuotationsFor] is called
     */
    private var getQuotationsForFailingID = -1L

    /**
     * Sets a param value for which a given call to this fake API is going to respond to
     * with an exception instead of return value. Use this to setup running environment for
     * your sample code.
     */
    fun setFailureIdsForRequests(getQuotationsForFailingId: Long = -1L) {
        this.getQuotationsForFailingID = getQuotationsForFailingId
    }

    suspend fun getUserById(userId: Long): User? {
        delay(300)
        return userIdToUser[userId]
    }

    suspend fun getUsers(): List<User> {
        delay(500)
        return userIdToUser.values.toList()
    }

    suspend fun getNumberOfQuotesOfUser(userId: Long): Int? {
        delay(50)
        return authorIdToQuotes[userId]?.size
    }

    suspend fun getQuotationsFor(authorId: Long): List<Quote>? {
        if (authorId == getQuotationsForFailingID) throw RuntimeException("failure")
        delay(600)
        return authorIdToQuotes[authorId]
    }

    /*
    Fake data is defined below
     */

    private val userIdToUser = hashMapOf(
        1L to User(1, "John"),
        2L to User(2, "Diana"),
        3L to User(3, "Ron"),
        4L to User(4, "NoName"),
        5L to User(5, "Silent one"),
    )

    private val authorIdToQuotes = hashMapOf(
        1L to listOf(Quote(1, "Carpe diem!"), Quote(1, "Spaghetti bolognese!")),
        2L to listOf(Quote(2, "Lorem Ipsum!")),
        3L to listOf(Quote(3, "Pork is good!")),
        4L to listOf(Quote(4, "Not much to say"), Quote(4, "bb || __")),
        5L to listOf(),
    )
}