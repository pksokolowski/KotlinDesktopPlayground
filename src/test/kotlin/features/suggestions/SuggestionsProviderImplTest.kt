package features.suggestions

import org.junit.Assert.assertEquals
import org.junit.Test

class SuggestionsProviderImplTest {
    @Test
    fun `When suggestions source contains 1 element, it is always suggested when initial characters match`() {
        givenProviderWithSuggestions("cat")
            .setInputAndExpect("c", "cat")
    }


    private fun givenProviderWithSuggestions(vararg suggestions: String) = SuggestionsProviderImpl().apply {
        setSuggestions(suggestions.asList())
    }

    private fun SuggestionsProvider.setInputAndExpect(newInput: String, expected: String?) = this.apply {
        val actual = suggest(newInput)
        assertEquals(expected, actual)
    }
}