package features.suggestions

import org.junit.Assert.assertEquals
import org.junit.Test

class SuggestionsProviderImplTest {
    @Test
    fun `When suggestions source contains 1 element, it is suggested when initial character matches`() {
        givenSuggestions("cat")
            .setInputAndExpect("c", null)
            .setInputAndExpect("ca", "cat")
    }

    @Test
    fun `matches input equal to suggestion with one missing character (last)`() {
        givenSuggestions("catrina")
            .setInputAndExpect("catrin", "catrina")
    }

    @Test
    fun `When multiple suggestions are available in source, it displays the one starting with hint's characters`() {
        givenSuggestions("duck", "dog")
            .setInputAndExpect("d", null)
            .setInputAndExpect("du", "duck")
            .setInputAndExpect("duc", "duck")
    }

    @Test
    fun `On 2 conflicting suggestions, returns null, also handles transition from one suggestion to the other`() {
        givenSuggestions("duck", "dump")
            .setInputAndExpect("d", null)
            .setInputAndExpect("du", null)
            .setInputAndExpect("duc", "duck")
            .setInputAndExpect("du", null)
            .setInputAndExpect("dum", "dump")
    }

    private fun givenSuggestions(vararg suggestions: String) = SuggestionsProviderImpl().apply {
        setSuggestions(suggestions.asList())
    }

    private fun SuggestionsProvider.setInputAndExpect(newInput: String, expected: String?) = this.apply {
        val actual = suggest(newInput)
        assertEquals("failed for input: <$newInput>", expected, actual)
    }
}