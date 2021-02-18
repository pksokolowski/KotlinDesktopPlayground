package features.countries.api.response

import com.google.gson.Gson
import com.google.gson.JsonArray
import features.countries.api.WorldBankApiParser
import org.junit.Assert.assertEquals
import org.junit.Test


class GetCountryInfoResponseTest {
    private val objectMapper = Gson()

    @Test
    fun `check if json returned from api is reflected correctly by DTO`() {
        val dto = objectMapper.fromJson(responseBr, JsonArray::class.java)
        val data = WorldBankApiParser(objectMapper).parse(dto) ?: throw RuntimeException("No way to parse")
        assertEquals("Brazil", data.name)
    }
}

private val responseBr = """
 [
  {
    "page": 1,
    "pages": 1,
    "per_page": "50",
    "total": 1
    },
    [
      {
        "id": "BRA",
        "iso2Code": "BR",
        "name": "Brazil",
        "region": {
          "id": "LCN",
          "iso2code": "ZJ",
          "value": "Latin America & Caribbean (all income levels)"
        },
        "adminregion": {
          "id": "LAC",
          "iso2code": "XJ",
          "value": "Latin America & Caribbean (developing only)"
        },
        "incomeLevel": {
          "id": "UMC",
          "iso2code": "XT",
          "value": "Upper middle income"
        },
        "lendingType": {
          "id": "IBD",
          "iso2code": "XF",
          "value": "IBRD"
        },
        "capitalCity": "Brasilia",
        "longitude": "-47.9292",
        "latitude": "-15.7801"
      }
    ]
  ]
        """.trimIndent()