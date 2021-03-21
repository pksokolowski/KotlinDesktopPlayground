package features.coroutines.domain.samples

import features.coroutines.domain.custom.groupN
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

        val availableProcessors = Runtime.getRuntime().availableProcessors()
        val remainder = number.remainder(availableProcessors.toBigInteger())
        val itemsPerWorker = number / availableProcessors.toBigInteger()
        val expectedGroups = if (remainder == 0.toBigInteger()) availableProcessors else availableProcessors + 1

        number.indexedEmissions()
            .groupN(itemsPerWorker, 500) // number / availableProcessors.toBigInteger()
            .filterNot { it.isEmpty() }
            .flatMapMerge(availableProcessors) { numbers ->
                flow {
                    if (numbers.isNotEmpty()) {
                        emit(productOf(numbers))
                    }
                }
            }
            .flowOn(Dispatchers.Default)
            .groupN(expectedGroups, Long.MAX_VALUE)
            .map { products ->
                productOf(products)
            }
            .onEach { result ->
                output("result is $result")
            }
            .launchIn(scope)

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


}