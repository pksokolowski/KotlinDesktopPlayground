package features.countries.presentation

import features.countries.use_cases.GetCountryInfoGivenCountryCodeUseCase
import features.countries.model.CountryInfo
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import navigation.NavDestination
import navigation.Navigator

class CountriesViewModel(
    private val navigator: Navigator,
    private val getCountryInfoUseCase: GetCountryInfoGivenCountryCodeUseCase
) : ICountriesViewModel {
    private val coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private val countryCodeMatcher = Regex("[a-zA-Z]{0,2}")

    override val countryCodeInputText = MutableStateFlow("")
    override val countryInfo = MutableStateFlow<CountryInfo?>(null)
    override val isLoading = MutableStateFlow(false)
    override val error = MutableStateFlow<Error?>(null)

    override fun setCountryCodeInput(code: String) {
        if (!code.matches(countryCodeMatcher)) return

        error.value = null
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
            val info = try {
                isLoading.value = true
                getCountryInfoUseCase.getInfo(countryCode)
            } catch (e: Exception) {
                error.value = Error.FailedToObtainCountryData
                null
            } finally {
                isLoading.value = false
            }
            countryInfo.value = info
        }
    }

    sealed class Error {
        object FailedToObtainCountryData : Error()
    }
}