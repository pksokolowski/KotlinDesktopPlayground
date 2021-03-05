package features.suggestions

class SuggestionsProviderImpl : SuggestionsProvider {
    private val signatureToSuggestionMap = hashMapOf<String, String?>()

    override fun suggest(hint: String): String? {
        return signatureToSuggestionMap[hint]
    }

    override fun setSuggestions(suggestions: List<String>) {
        suggestions.forEach(::populateHashMapFor)
    }

    private fun populateHashMapFor(input: String) {
        val signatures = getSignatures(input)
        signatures.forEach { signature ->
            if (signatureToSuggestionMap.containsKey(signature)) {
                signatureToSuggestionMap[signature] = null
            } else {
                signatureToSuggestionMap[signature] = input
            }
        }
    }

    private fun getSignatures(input: String, minLen: Int = 2): List<String> {
        if (input.length < minLen) return emptyList()

        val signatures = mutableListOf<String>()
        for (i in minLen until input.length) {
            signatures.add(input.substring(0, i))
        }

        return signatures
    }
}