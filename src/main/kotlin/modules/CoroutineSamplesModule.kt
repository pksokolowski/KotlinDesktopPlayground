package modules

import features.coroutines.domain.samples.CoroutinesSample
import features.coroutines.domain.samples.FanOutCoroutinesSample
import features.coroutines.domain.samples.StructuredConcurrencyCoroutinesSample
import features.coroutines.domain.samples.WelcomeCoroutinesSample
import features.coroutines.presentation.CoroutinesViewModel
import features.coroutines.presentation.ICoroutinesViewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val coroutineSamplesModule = module {
    factory { CoroutinesViewModel(get(), get(), get()) as ICoroutinesViewModel }
    factory { getAll<CoroutinesSample>() }

    factory { WelcomeCoroutinesSample() } bind CoroutinesSample::class
    factory { FanOutCoroutinesSample() } bind CoroutinesSample::class
    factory { StructuredConcurrencyCoroutinesSample() } bind CoroutinesSample::class
}
