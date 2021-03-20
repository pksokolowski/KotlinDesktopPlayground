package features.coroutines.domain.custom

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class StubbornCoroutineBuilderKtTest {

    @Test
    fun `stubborn builder runs code properly`() {
        var mutableStateChanged = false
        runBlocking {
            stubborn(1) {
                mutableStateChanged = true
            }
        }
        assertEquals(true, mutableStateChanged)
    }

    @Test
    fun `stubborn throws exception on the first failed attempt when maxAttempts is 1`() {
        var attemptsMade = 0
        try {
            runBlocking {
                stubborn(1) {
                    attemptsMade++
                    throw AttemptFailedException()
                }
            }
            fail()
        } catch (attemptFailed: AttemptFailedException) {

        }
        assertEquals(1, attemptsMade)
    }

    @Test
    fun `stubborn throws exception on the second failed attempt when maxAttempts is 2`() {
        var attemptsMade = 0
        try {
            runBlocking {
                stubborn(2) {
                    attemptsMade++
                    throw AttemptFailedException()
                }
            }
            fail()
        } catch (attemptFailed: AttemptFailedException) {

        }
        assertEquals(2, attemptsMade)
    }

    @Test
    fun `stubborn only runs to the first successful attempt, despite further attempts being allowed by maxAttempts`() {
        var attemptsMade = 0
        try {
            runBlocking {
                stubborn(10) {
                    attemptsMade++
                    if (attemptsMade < 5)
                        throw AttemptFailedException()
                }
            }
        } catch (attemptFailed: AttemptFailedException) {

        }
        assertEquals(5, attemptsMade)
    }

    class AttemptFailedException : Exception()
}