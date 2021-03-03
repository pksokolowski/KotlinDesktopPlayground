package features.coroutines.domain.samples

import kotlinx.coroutines.*
import java.util.concurrent.Executors

class StructuredConcurrencyCoroutinesSample : CoroutinesSample(
    command = "structured",
    explanation = """
        Structured concurrency sample. 
        
        SC being the main feature of coroutines, it has its honorable place
        among the samples. 
        Here we explore how various sequential and concurrent dummy tasks 
        are waited for at set points where the paths of execution converge,
        ensuring that at certain, known points in our code, we will have previous
        tasks finished - otherwise coroutines framework would suspend and wait 
        till they are all ready.
    """.trimIndent()
) {
    override fun start(scope: CoroutineScope, args: List<String>, output: (String) -> Unit) {
        val singleThreadedDispatcher = Executors.newFixedThreadPool(1).asCoroutineDispatcher()

        scope.launch {
            // setup util functions for dummy work
            suspend fun blockingWork(id: String, duration: Milliseconds) = work(id, duration, true, output)
            suspend fun suspendingWork(id: String, duration: Milliseconds) = work(id, duration, false, output)

            coroutineScope {
                launch {
                    suspendingWork("job1", 500.millis)
                }
                output("--- after launch block, where job 1 is started ---")
                output(
                    """
                    The job1 didn't finish yet, in fact it might not yet started at all, it runs concurrently with later statements within scope.
                    Note: this is because launch's Job, given it's a child job to the outer scope's Job, is bound to finish within the outer scope
                """.trimIndent()
                )
                output("")
            }

            output("--- here we are after the outer scope ---")
            output(
                """
                should we start a new coroutine here, with sufficiently long running time, it could keep running
                past all the subsequently launched coroutines within current, most general scope.
                lets in fact start a 5 sec job here
            """.trimIndent()
            )
            launch {
                blockingWork("5-Sec work", 5000.millis)
            }

            output("\n--- and now lets carry on with other stuff ---")
            output(
                """
                namely, lets start a pair of operations - one short and one long
                to make things more interesting, lets run them on two separate thread pools
                They will still finish in a knonw place in our code, as we will put them into a 
                common coroutine scope- aka their Jobs will have the same parent Job, and the parent Job 
                will be used to enforce structured concurrency, that is to wait on both before leaving the 
                scope's closure.
            """.trimIndent()
            )

            coroutineScope {
                launch { blockingWork("operation1", 400.millis) }
                launch(singleThreadedDispatcher) { blockingWork("operation2", 100.millis) }
            }
            output(
                """
                --- now we are outside the scope for the operation1 and operation2 ---
            """.trimIndent()
            )

            output(
                """
                
                Lets try withContext {} instead of coroutineScope {} and see if it changes anything
            """.trimIndent()
            )

            withContext(CoroutineName("some name")) {
                launch { suspendingWork("work within withContext", 200.millis) }
            }
            output("--- this is after the withContext()'s block.")

            output("""
                
                Using withContext instead of launch will still do the trick,
                because withContext creates a new coroutine scope (and a new job) (but not a new coroutine)
                lets start work directly in withContext block now
            """.trimIndent())

            withContext(CoroutineName("another name")){
                suspendingWork("work started directly in withContext, no launch", 1500.millis)
            }
            output("--- this is after the withContext block")
        }
    }

    private suspend fun work(workId: String, duration: Milliseconds, block: Boolean, output: (String) -> Unit) {
        if (block) {
            Thread.sleep(duration.value)
        } else {
            delay(duration.value)
        }
        output("!!!$workId finished")
    }

    private data class Milliseconds(val value: Long)

    private val Int.millis: Milliseconds
        get() = Milliseconds(this.toLong())
}