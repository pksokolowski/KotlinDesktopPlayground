package features.countries.use_cases

import features.countries.api.CountriesService
import features.countries.api.WorldBankApiParser
import features.countries.model.CountryInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Fetches country info based on an ISO 3166-1 alpha-2 country code.
 */
class GetCountryInfoGivenCountryCodeUseCase(
    private val countriesService: CountriesService,
    private val worldBankApiParser: WorldBankApiParser
) {
    suspend fun getInfo(countryCode: String) = withContext(Dispatchers.IO) {
        val infoDto = countriesService.getCountryInfo(countryCode)
        val country = worldBankApiParser.parse(infoDto) ?: return@withContext null

        CountryInfo(
            name = country.name,
            capitalCity = country.capitalCity,
            incomeLevel = country.incomeLevel.value,
        )
    }
}