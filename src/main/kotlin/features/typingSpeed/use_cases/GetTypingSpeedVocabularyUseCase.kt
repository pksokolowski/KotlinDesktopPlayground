package features.typingSpeed.use_cases

import features.typingSpeed.repository.WordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class GetTypingSpeedVocabularyUseCase(
    private val wordsRepository: WordsRepository
) {
    private val _vocabulary = MutableStateFlow(listOf<String>())

    suspend fun getVocabulary() = withContext(Dispatchers.IO) {
        _vocabulary.value = wordsRepository.getAllWords()
        _vocabulary.asStateFlow()
    }

    suspend fun saveWord(word: String) = withContext(Dispatchers.IO) {
        wordsRepository.saveWord(word)
        getVocabulary()
    }
}