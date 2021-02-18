package features.countries.api.reponse

data class GetCountryInfoResponse(
    val id: String,
    val iso2Code: String,
    val name: String,
    val region: Adminregion,
    val adminregion: Adminregion,
    val incomeLevel: Adminregion,
    val lendingType: Adminregion,
    val capitalCity: String,
    val longitude: String,
    val latitude: String
) {
    data class Adminregion(
        val id: String,
        val iso2Code: String,
        val value: String
    )
}