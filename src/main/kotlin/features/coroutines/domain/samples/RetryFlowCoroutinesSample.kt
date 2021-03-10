package features.coroutines.domain.samples

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlin.random.Random

class RetryFlowCoroutinesSample : CoroutinesSample(
    command = "retryFlow",
    explanation = """
        Running a flow with a considerable risk of failing, and a retry policy or restarting it
        after a short delay
    """.trimIndent()
) {
    override fun start(scope: CoroutineScope, args: List<String>, output: (String) -> Unit) {

        val sourceStream = flow {
            while (currentCoroutineContext().isActive) {
                delay(1000)
                val randomNum = Random.nextInt(40)
                require(randomNum < 30) {
                    output("crash!")
                    "Failure"
                }
                emit(randomNum)
            }
        }

        fun getSelfRebuildingStream(): Flow<Int> = sourceStream
            .catch {
                delay(2000)
                emitAll(getSelfRebuildingStream())
            }

        getSelfRebuildingStream()
            .onEach { output("$it") }
            .launchIn(scope)
    }
}