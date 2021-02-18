package features.countries.api

import com.google.gson.JsonArray
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesService {
    @GET("{iso2CountryCode}?format=json")
    suspend fun getCountryInfo(@Path("iso2CountryCode") iso2CountryCode: String): JsonArray
}