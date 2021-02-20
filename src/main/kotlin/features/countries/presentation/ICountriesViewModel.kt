package features.countries.presentation

import features.countries.model.CountryInfo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

interface ICountriesViewModel {
    val countryCodeInputText: MutableStateFlow<String>
    val countryInfo: MutableStateFlow<CountryInfo?>
    val isLoading: MutableStateFlow<Boolean>
    val error: MutableSharedFlow<CountriesViewModel.Error?>
    fun goBack()
    fun setCountryCodeInput(code: String)
}