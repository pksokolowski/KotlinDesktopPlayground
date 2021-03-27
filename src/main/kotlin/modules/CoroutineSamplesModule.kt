package modules

import features.coroutines.api.FakeQuotationsApi
import features.coroutines.domain.samples.*
import features.coroutines.presentation.CoroutinesViewModel
import features.coroutines.presentation.ICoroutinesViewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val coroutineSamplesModule = module {
    factory { CoroutinesViewModel(get(), get(), get()) as ICoroutinesViewModel }
    factory { getAll<CoroutinesSample>() }
    factory { FakeQuotationsApi() }

    factory { WelcomeCoroutinesSample() } bind CoroutinesSample::class
    factory { FanOutCoroutinesSample() } bind CoroutinesSample::class
    factory { StructuredConcurrencyCoroutinesSample() } bind CoroutinesSample::class
    factory { RetryFlowCoroutinesSample() } bind CoroutinesSample::class
    factory { SupervisorScopeCoroutinesSample() } bind CoroutinesSample::class
    factory { ApiCallsCoroutinesSample(get()) } bind CoroutinesSample::class
    factory { RetryBuilderCoroutinesSample() } bind CoroutinesSample::class
    factory { FactorialCoroutinesSample() } bind CoroutinesSample::class
    factory { SuspendingListenerCoroutinesSample() } bind CoroutinesSample::class
}
