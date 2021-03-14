package features.coroutines.domain.samples

import features.coroutines.api.FakeQuotationsApi
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Exception

class ApiCallsCoroutinesSample(
    private val fakeQuotationsApi: FakeQuotationsApi
) : CoroutinesSample(
    command = "apiCalls",
    explanation = """
        running a set of multiple fake api/db calls, making simple branching decisions.
        
        Note: use of flatMapMerge (for performance) is not encouraged as it breaches out of the 
        simple, linear reasoning which would otherwise be possible, however, the performance benefits are
        substantial. For similar cases using flows might be questionable to begin with though.
        
        Therefore this sample also features an alternative implementation, where the 
        parallel decomposition is done in a "proper" way.  To run the alternative code, add
        alt parameter to the command.
        
        a third alternative - not to be confused with the binary operator, nor with alternative's Latin ancestor, which 
        indeed took 2 :P -  is implemented additionally, as an experimental modification of the default approach, 
        providing, perhaps, a bit cleaner code. To run it, use "alt2" param.
        
        Params:
        mode - 
            "default" - flows-based implementation 
            "alt" - no-flows solution
            "alt2" = flows-based, potentially cleaner code
            
        shouldFail - y/n indicating whether or not the fake api
        should throw an exception
                 
    """.trimIndent()
) {
    @FlowPreview
    override fun start(scope: CoroutineScope, args: List<String>, output: (String) -> Unit) {
        val mode = args.getOrNull(0) ?: "default"
        val shouldFail = (args.getOrNull(1) ?: "n") == "y"

        output("running fake api usage sample, with mode = $mode and shouldFail = $shouldFail\n")

        if (shouldFail) {
            fakeQuotationsApi.setFailureIdsForRequests(
                getQuotationsForFailingId = 3
            )
        }

        data class AuthorAndQuotes(val author: String, val quotes: List<String>)
        data class AuthorAndQuote(val author: String, val quote: String)

        when (mode) {
            "default" -> {

                getAllUsers()
                    .flatMapMerge(Runtime.getRuntime().availableProcessors()) { user ->
                        flow {
                            val quotes = fakeQuotationsApi.getQuotationsFor(user.id)
                                ?.map { it.content } ?: listOf()
                            emit(AuthorAndQuotes(user.name, quotes))
                        }
                    }
                    .flatMapConcat { authorAndQuotes ->
                        flow {
                            authorAndQuotes.quotes.forEach { quote ->
                                emit(AuthorAndQuote(authorAndQuotes.author, quote))
                            }
                        }
                    }
                    .catch {
                        output("failure!")
                    }
                    .onEach {
                        output("quote by: ${it.author} -> ${it.quote}")
                    }
                    .launchIn(scope)

            }
            "alt" -> {

                scope.launch(Dispatchers.IO) {
                    val quotes = try {
                        fakeQuotationsApi.getUsers()
                            .map { user ->
                                async {
                                    val quotes = fakeQuotationsApi.getQuotationsFor(user.id)
                                        ?.map { it.content } ?: listOf()
                                    AuthorAndQuotes(user.name, quotes)
                                }
                            }
                            .awaitAll()
                            .flatMap { (author, quotes) ->
                                quotes
                                    .map { quote ->
                                        AuthorAndQuote(author, quote)
                                    }
                            }
                    } catch (e: Exception) {
                        output("failure")
                        listOf()
                    }

                    quotes.forEach {
                        output("quote by: ${it.author} -> ${it.quote}")
                    }
                }

            }

            "alt2" -> {

                getAllUsers()
                    .map { user ->
                        flow {
                            val quotes = fakeQuotationsApi.getQuotationsFor(user.id)
                                ?.map { it.content } ?: listOf()

                            quotes.forEach { quote ->
                                emit(AuthorAndQuote(user.name, quote))
                            }
                        }
                    }
                    .flattenMerge(Runtime.getRuntime().availableProcessors())
                    .catch {
                        output("failure!")
                    }
                    .onEach {
                        output("quote by: ${it.author} -> ${it.quote}")
                    }
                    .launchIn(scope)

            }
        }
    }


    private fun getAllUsers() = flow {
        val users = fakeQuotationsApi.getUsers()
        users.forEach { emit(it) }
    }
}