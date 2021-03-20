package features.coroutines.domain.custom

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun CoroutineScope.stubborn(
    maxAttempts: Int,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = launch(context, start) {
    repeat(maxAttempts) { attempt ->
        try {
            block()
            return@launch
        } catch (e: Exception) {
            if (attempt == maxAttempts - 1) throw  e
        }
    }
}