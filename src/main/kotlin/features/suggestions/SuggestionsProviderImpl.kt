package features.suggestions

class SuggestionsProviderImpl : SuggestionsProvider {
    private var suggestions = listOf<String>()

    override fun suggest(hint: String): String {
        return "kop"
    }

    override fun setSuggestions(suggestions: List<String>) {
        this.suggestions = suggestions
    }
}