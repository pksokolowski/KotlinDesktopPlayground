package features.coroutines.domain.samples

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AccidentalUncancellabilityCoroutinesSample : CoroutinesSample(
    command = "accidentallyBreakingCancellation",
    explanation = """
        This sample showcases one way to render an otherwise proper coroutine uncancellable
        while trying to handle exception. The issue originates in the way cancellation is 
        propagated in coroutines - that is through CancellationException. 
        Try-catching all exceptions will also catch the CancellationException and prevent
        cancellation of our coroutine, so it will keep running.
        
        params:
        applyFix - y/n when set to y, fix will be applied and cancellation exception will be re-thrown
    """.trimIndent()
) {
    override fun start(scope: CoroutineScope, args: List<String>, output: (String) -> Unit) {
        val applyFix = args.getOrNull(0) == "y"

        val job = scope.launch {
            try {
                delay(200)
                output("finished job despite it being cancelled!")
            } catch (c: CancellationException) {
                // this catch block is meant for catching and re-throwing cancellation exception
                output("caught exception: ${c::class.simpleName}")
                if (applyFix) throw c
            } catch (e: Exception) {
                // this catch block is meant to handle regular exception in the code
                output("caught exception: ${e::class.simpleName}")
            }
            // alternatively, ensureActive() could be called after the try-catch, at least in some scenarios it would
            // suffice.
            output("still did something else after cancellation, potentially crashing the app")
        }

        scope.launch {
            delay(100)
            job.cancel()
            output("cancelled job")
        }
    }
}
