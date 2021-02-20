package features.countries.presentation

import features.countries.model.CountryInfo
import kotlinx.coroutines.flow.StateFlow

interface ICountriesViewModel {
    val countryCodeInputText: StateFlow<String>
    val countryInfo: StateFlow<CountryInfo?>
    val isLoading: StateFlow<Boolean>
    val error: StateFlow<CountriesViewModel.Error?>
    fun goBack()
    fun setCountryCodeInput(code: String)
}