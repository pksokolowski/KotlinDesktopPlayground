package features.countries.presentation

import features.countries.use_cases.GetCountryInfoGivenISO2CountryCodeUseCase
import features.countries.model.CountryInfo
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import navigation.NavDestination
import navigation.Navigator

class CountriesViewModel(
    private val navigator: Navigator,
    private val getCountryInfoUseCase: GetCountryInfoGivenISO2CountryCodeUseCase
) : ICountriesViewModel {
    private val coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private val countryNameMatcher = Regex("[a-zA-Z]{0,2}")

    override val countryCodeInputText = MutableStateFlow("")
    override val countryInfo = MutableStateFlow<CountryInfo?>(null)

    override fun setCountryCodeInput(code: String) {
        if (!code.matches(countryNameMatcher)) return

        countryCodeInputText.value = code

        if (code.length == 2) {
            fetchCountryDataForCode()
        } else {
            countryInfo.value = null
        }
    }

    override fun goBack() {
        coroutineScope.coroutineContext.cancelChildren()
        navigator.navigateTo(NavDestination.Previous)
    }

    private fun fetchCountryDataForCode() {
        val countryCode = countryCodeInputText.value
        coroutineScope.launch {
            val info = getCountryInfoUseCase.getInfo(countryCode)
            countryInfo.value = info
        }
    }
}