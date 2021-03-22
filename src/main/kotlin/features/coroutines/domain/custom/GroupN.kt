package features.coroutines.domain.custom

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigInteger

fun <T> Flow<T>.groupN(n: BigInteger, partialBufferFlushDelay: Long? = null): Flow<List<T>> {
    val upstream = this
    var buffer = mutableListOf<T>()
    var partialFlushJob: Job? = null

    return channelFlow {
        upstream.collect { value ->
            buffer.add(value)
            if (buffer.size.toBigInteger() == n) {
                partialFlushJob?.cancel()
                send(buffer)
                buffer = mutableListOf()
            } else if (partialBufferFlushDelay != null) {
                partialFlushJob?.cancel()
                partialFlushJob = launch {
                    delay(partialBufferFlushDelay)
                    if (buffer.isEmpty()) return@launch
                    send(buffer)
                    buffer = mutableListOf()
                }
            }
        }
    }
}

fun <T> Flow<T>.groupN(n: Int, partialBufferFlushDelay: Long? = null): Flow<List<T>> =
    this.groupN(n.toBigInteger(), partialBufferFlushDelay)