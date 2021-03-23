package features.coroutines.domain.samples

import features.coroutines.domain.custom.groupN
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.math.BigInteger

class FactorialCoroutinesSample : CoroutinesSample(
    command = "factorial",
    explanation = """
        Computes factorial on multiple threads
        
        params
        number - BigInteger whose factorial we wan to compute. 
    """.trimIndent()
) {
    override fun start(scope: CoroutineScope, args: List<String>, output: (String) -> Unit) {
        val number = args.firstOrNull()?.toBigIntegerOrNull() ?: 100.toBigInteger()

        output("computing factorial of $number")

        val processorsCount = Runtime.getRuntime().availableProcessors()

        val concurrency = if (number < processorsCount.toBigInteger() * 2.toBigInteger()) 1 else processorsCount
        val remainder = number.remainder(concurrency.toBigInteger())
        val itemsPerWorker = number / concurrency.toBigInteger()
        val expectedGroupsCount = if (remainder == 0.toBigInteger()) concurrency else concurrency + 1

        number.indexedEmissions()
            .groupN(itemsPerWorker, 500) // number / availableProcessors.toBigInteger()
            .filterNot { it.isEmpty() }
            .flatMapMerge(concurrency) { numbers ->
                flow {
                    if (numbers.isNotEmpty()) {
                        emit(productOf(numbers))
                    }
                }
            }
            .flowOn(Dispatchers.Default)
            .groupN(expectedGroupsCount)
            .map { products ->
                productOf(products)
            }
            .onEach { result ->
                output("\nresult is:\n$result")
            }
            .launchIn(scope)

        scope.launch {
            val alternativeResult = computeFactorial(number)
            output("\nalternative solution's result is:\n$alternativeResult")
        }
    }

    private fun productOf(integers: List<BigInteger>): BigInteger {
        require(integers.isNotEmpty()) { "product of an empty list of numbers cannot be computed" }
        if (integers.size == 1) {
            return (integers.first())
        }

        var product = integers.first()
        for (i in 1 until integers.size) {
            product *= integers[i]
        }
        return product
    }

    private fun BigInteger.indexedEmissions() = this.let { number ->
        flow {
            var i = 1.toBigInteger()
            while (i <= number) {
                emit(i)
                i++
            }
        }
    }

    private suspend fun CoroutineScope.computeFactorial(number: BigInteger) = withContext(Dispatchers.Default) {
        data class BigIntRange(val start: BigInteger, val end: BigInteger)

        val processorsCount = Runtime.getRuntime().availableProcessors()

        val concurrency = if (number < processorsCount.toBigInteger() * 2.toBigInteger()) 1 else processorsCount
        val itemsPerWorker = number / concurrency.toBigInteger()
        val remainder = number.remainder(concurrency.toBigInteger())
        val expectedGroupsCount =
            if (remainder == 0.toBigInteger()) concurrency else concurrency + 1

        var rangeStart = 1.toBigInteger()
        val rangesToMultiply = List<BigIntRange>(expectedGroupsCount) {
            val unboundEnd = (rangeStart + itemsPerWorker - 1.toBigInteger())
            val rangeEnd = unboundEnd.min(number)
            val range = BigIntRange(rangeStart, rangeEnd)

            rangeStart = rangeEnd + 1.toBigInteger()
            range
        }

        rangesToMultiply.map { range ->
            async {
                var product = range.start
                var i = range.start + 1.toBigInteger()
                while (i <= range.end) {
                    ensureActive()
                    product *= i
                    i++
                }
                product
            }
        }.awaitAll()
            .fold(1.toBigInteger()) { acc, value ->
                acc * value
            }
    }
}