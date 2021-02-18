package features.countries.presentation

import features.countries.model.CountryInfo
import kotlinx.coroutines.flow.MutableStateFlow

interface ICountriesViewModel {
    val countryCodeInputText: MutableStateFlow<String>
    val countryInfo: MutableStateFlow<CountryInfo?>
    fun goBack()
    fun setCountryCodeInput(code: String)
}