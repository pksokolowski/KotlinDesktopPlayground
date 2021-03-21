package features.coroutines.domain.custom

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigInteger

fun <T> Flow<T>.groupN(n: BigInteger, partialBufferFlushDelay: Long): Flow<List<T>> {
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
            } else {
                partialFlushJob?.cancel()
                partialFlushJob = launch {
                    delay(partialBufferFlushDelay)
                    if (buffer.isEmpty()) return@launch
                    send(buffer)
                    buffer = mutableListOf()
                    partialFlushJob?.cancel()
                }
            }
        }
    }
}

fun <T> Flow<T>.groupN(n: Int, partialBufferFlushDelay: Long): Flow<List<T>> =
    this.groupN(n.toBigInteger(), partialBufferFlushDelay)