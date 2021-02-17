package modules

import features.mainScreen.presentation.IMainViewModel
import features.mainScreen.presentation.MainViewModel
import features.typingSpeed.`use-cases`.GetTypingSpeedVocabularyUseCase
import features.typingSpeed.db.WordsDatabase
import features.typingSpeed.presentation.ITypingSpeedViewModel
import features.typingSpeed.presentation.TypingSpeedViewModel
import features.typingSpeed.repository.WordsRepository
import navigation.Navigator
import navigation.ScreenBackStack
import org.koin.dsl.module

val mainModule = module {
    single { Navigator() }
    factory { ScreenBackStack() }
    factory { MainViewModel(get()) as IMainViewModel }
    factory { TypingSpeedViewModel(get(), get()) as ITypingSpeedViewModel }

    factory { WordsDatabase() }
    factory { WordsRepository(get()) }
    factory { GetTypingSpeedVocabularyUseCase(get()) }
}