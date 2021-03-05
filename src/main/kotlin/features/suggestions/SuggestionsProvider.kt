package features.suggestions

interface SuggestionsProvider {
    fun suggest(hint: String): String?
    fun setSuggestions(suggestions: List<String>)
}