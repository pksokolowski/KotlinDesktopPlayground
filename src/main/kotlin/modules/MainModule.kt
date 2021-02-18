package modules

import com.google.gson.Gson
import features.countries.use_cases.GetCountryInfoGivenISO2CountryCodeUseCase
import features.countries.api.CountriesService
import features.countries.api.WorldBankApiParser
import features.countries.presentation.CountriesViewModel
import features.countries.presentation.ICountriesViewModel
import features.mainScreen.presentation.IMainViewModel
import features.mainScreen.presentation.MainViewModel
import features.typingSpeed.use_cases.GetTypingSpeedVocabularyUseCase
import features.typingSpeed.db.WordsDatabase
import features.typingSpeed.presentation.ITypingSpeedViewModel
import features.typingSpeed.presentation.TypingSpeedViewModel
import features.typingSpeed.repository.WordsRepository
import navigation.Navigator
import navigation.ScreenBackStack
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val mainModule = module {
    single { Navigator() }
    factory { ScreenBackStack() }
    factory { MainViewModel(get()) as IMainViewModel }
    factory { TypingSpeedViewModel(get(), get()) as ITypingSpeedViewModel }

    factory { WordsDatabase() }
    factory { WordsRepository(get()) }
    factory { GetTypingSpeedVocabularyUseCase(get()) }

    factory { CountriesViewModel(get(), get()) as ICountriesViewModel }
}

val networkModule = module {
    factory {
        Retrofit.Builder()
            .baseUrl("http://api.worldbank.org/v2/country/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun provideCountriesService(retrofit: Retrofit): CountriesService = retrofit.create(CountriesService::class.java)

    factory { provideCountriesService(get()) }

    factory { Gson() }

    factory { WorldBankApiParser(get()) }

    factory { GetCountryInfoGivenISO2CountryCodeUseCase(get(), get()) }
}