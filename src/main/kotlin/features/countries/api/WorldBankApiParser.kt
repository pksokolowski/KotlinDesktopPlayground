package features.countries.api

import com.google.gson.Gson
import com.google.gson.JsonArray
import features.countries.api.reponse.GetCountryInfoResponse
import java.lang.Exception

class WorldBankApiParser(
    private val gson: Gson
) {
    fun parse(response: JsonArray): GetCountryInfoResponse? {
        return try {
            val countriesList = response[1].asJsonArray
            val country = countriesList.firstOrNull() ?: return null
            val data = country.asJsonObject
            gson.fromJson(data, GetCountryInfoResponse::class.java)
        } catch (e: Exception) {
            null
        }
    }
}