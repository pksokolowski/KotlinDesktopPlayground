package features.countries.presentation

import MainAppBar
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import navigation.Screen
import org.koin.java.KoinJavaComponent

class CountriesScreen : Screen {
    private val viewModel by KoinJavaComponent.inject(ICountriesViewModel::class.java)

    @Composable
    @Suppress("FunctionName")
    @ExperimentalCoroutinesApi
    override fun render() {
        MaterialTheme {
            val countryCodeInput = viewModel.countryCodeInputText.collectAsState()
            val countryInfo = viewModel.countryInfo.collectAsState()

            Scaffold(
                topBar = {
                    MainAppBar(
                        icon = Icons.Default.ArrowBack,
                        title = "Countries",
                        onIconClick = { viewModel.goBack() }
                    )
                }
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        countryInfo.value?.let { country ->
                            Text("name: ${country.name}")
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            TextField(
                                value = countryCodeInput.value,
                                onValueChange = { newText ->
                                    viewModel.setCountryCodeInput(newText)
                                }
                            )
                        }
                    }
                }
            }
        }

    }
}