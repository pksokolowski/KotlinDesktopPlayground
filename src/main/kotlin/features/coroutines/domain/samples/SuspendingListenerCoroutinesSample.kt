package features.coroutines.domain.samples

import features.coroutines.domain.samples.SuspendingListenerCoroutinesSample.Resource.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class SuspendingListenerCoroutinesSample : CoroutinesSample(
    command = "suspendingListener",
    explanation = """
     Working with APIs that are not designed to be used with coroutines 
     when we need to suspend till a listener responds, we can obtain 
     continuation object of a coroutine and manipulate it directly
     this way we can effectively suspend till the callback is fired.         
 """.trimIndent()
) {
    override fun start(scope: CoroutineScope, args: List<String>, output: (String) -> Unit) {

        val api = SomeApi()

        scope.launch {
            val result = suspendCoroutine<String> { continuation ->
                api.getSomethingById(BREAD, object : Callback {
                    override fun onResult(result: String) {
                        continuation.resume(result)
                    }

                    override fun onError(throwable: Throwable) {
                        continuation.resumeWithException(throwable)
                    }
                })
            }
            output("got: $result")
        }
    }

    private interface Callback {
        fun onResult(result: String)
        fun onError(throwable: Throwable)
    }

    private enum class Resource {
        BREAD,
        WINE
    }

    private class SomeApi() {
        fun getSomethingById(resource: Resource, callback: Callback) {
            Thread.sleep(2000)
            when (resource) {
                BREAD -> callback.onResult("some bread")
                WINE -> callback.onError(IllegalArgumentException("prohibition!"))
            }
        }
    }
}