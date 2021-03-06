package modules

import com.google.gson.Gson
import features.countries.api.CountriesService
import features.countries.api.WorldBankApiParser
import features.countries.use_cases.GetCountryInfoGivenCountryCodeUseCase
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    factory { GetCountryInfoGivenCountryCodeUseCase(get(), get()) }
}