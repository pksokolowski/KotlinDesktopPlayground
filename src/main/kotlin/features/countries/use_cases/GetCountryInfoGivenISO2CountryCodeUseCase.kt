package features.countries.use_cases

import features.countries.api.CountriesService
import features.countries.api.WorldBankApiParser
import features.countries.model.CountryInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCountryInfoGivenISO2CountryCodeUseCase(
    private val countriesService: CountriesService,
    private val worldBankApiParser: WorldBankApiParser
) {
    suspend fun getInfo(iso2CountryCode: String) = withContext(Dispatchers.IO) {
        val infoDto = countriesService.getCountryInfo(iso2CountryCode)
        val country = worldBankApiParser.parse(infoDto) ?: return@withContext null

        CountryInfo(
            name = country.name,
            capitalCity = country.capitalCity,
            incomeLevel = country.incomeLevel.value,
        )
    }
}