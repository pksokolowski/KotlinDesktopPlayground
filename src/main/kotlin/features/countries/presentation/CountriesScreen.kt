package features.countries.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import features.countries.model.CountryInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import navigation.Screen
import org.koin.java.KoinJavaComponent
import views.ScreenContent

class CountriesScreen : Screen {
    private val viewModel by KoinJavaComponent.inject(ICountriesViewModel::class.java)

    @Composable
    @Suppress("FunctionName")
    @ExperimentalCoroutinesApi
    override fun render() {
        MaterialTheme {
            val countryCodeInput = viewModel.countryCodeInputText.collectAsState()
            val countryInfo = viewModel.countryInfo.collectAsState()
            val isLoading = viewModel.isLoading.collectAsState()
            val error = viewModel.error.collectAsState()

            ScreenContent(
                title = "Countries",
                onBackPress = { viewModel.goBack() }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier
                            .fillMaxHeight(0.4f)
                    ) {
                        CountryData(
                            countryInfo.value,
                            isLoading.value,
                            error.value
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CountryCodeInput(countryCodeInput.value)
                    }
                }
            }
        }
    }

    @Composable
    @Suppress("FunctionName")
    private fun CountryCodeInput(countryCodeInput: String) {
        OutlinedTextField(
            value = countryCodeInput,
            label = { Text("ISO 3166-1 alpha-2 country code") },
            onValueChange = { newText ->
                viewModel.setCountryCodeInput(newText)
            },
            modifier = Modifier
                .padding(top = 8.dp)
        )
    }

    @Composable
    @Suppress("FunctionName")
    private fun CountryData(
        countryInfo: CountryInfo?,
        isLoading: Boolean,
        error: CountriesViewModel.Error?
    ) {
        countryInfo.let { country ->
            if (country != null) {
                listOf(
                    "name" to country.name,
                    "capitol city" to country.capitalCity,
                    "income level" to country.incomeLevel
                ).let { rowsData ->
                    InfoRows(rowsData)
                }
            } else {
                when {
                    isLoading -> {
                        CircularProgressIndicator()
                    }
                    error != null -> {
                        Text(
                            text = "error",
                            style = TextStyle(
                                color = Color.Red,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    else -> {
                        Text("try \"pl\", \"gb\", \"de\"")
                    }
                }
            }
        }
    }

    @Composable
    @Suppress("FunctionName")
    private fun InfoRows(
        keyValueRows: List<Pair<String, String>>
    ) {
        Column {
            keyValueRows.forEach { rowData ->
                val (title, value) = rowData
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(title)
                    Text(
                        text = value,
                        style = TextStyle(
                            color = Color(100, 50, 50),
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .padding(start = 8.dp)
                    )
                }
            }
        }
    }

}