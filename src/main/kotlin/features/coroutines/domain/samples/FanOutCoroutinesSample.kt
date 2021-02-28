package features.coroutines.domain.samples

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach

class FanOutCoroutinesSample : CoroutinesSample(
    command = "fanout",
    explanation = """
        Runs a set of heavy operations on a set number of workers in a fan-out fashion.
        
        The workload is shared between the workers, as each of them picks 
        the next waiting task from a channel containing all enqueued tasks.
        
        args:
        workersCount
        workItemsCount
    """.trimIndent()
) {
    override fun start(scope: CoroutineScope, args: List<String>, output: (String) -> Unit) {
        val workersCount = args.getOrNull(0)?.toIntOrNull() ?: 2
        val workItemsCount = args.getOrNull(1)?.toIntOrNull() ?: 10
        output("Running $workItemsCount instances of a heavy work with $workersCount worker(s))")

        val tasksChannel = Channel<Int>()

        fun CoroutineScope.launchWorker(channel: ReceiveChannel<Int>) =
            launch {
                channel.consumeEach { item ->
                    ensureActive()
                    heavyWork(item)
                    ensureActive()
                    output("Got result for item = $item")
                }
            }

        scope.launch {
            repeat(workersCount) {
                launchWorker(tasksChannel)
            }

            repeat(workItemsCount) { item ->
                tasksChannel.send(item)
            }
        }
    }

    private fun heavyWork(input: Int): Int {
        Thread.sleep(1000)
        return input
    }
}