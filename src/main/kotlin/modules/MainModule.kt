package modules

import features.coroutines.presentation.CoroutinesViewModel
import features.coroutines.presentation.ICoroutinesViewModel
import features.countries.presentation.CountriesViewModel
import features.countries.presentation.ICountriesViewModel
import features.lists.IListsViewModel
import features.lists.ListsViewModel
import features.mainScreen.presentation.IMainViewModel
import features.mainScreen.presentation.MainViewModel
import features.typingSpeed.db.WordsDatabase
import features.typingSpeed.presentation.ITypingSpeedViewModel
import features.typingSpeed.presentation.TypingSpeedViewModel
import features.typingSpeed.repository.WordsRepository
import features.typingSpeed.use_cases.GetTypingSpeedVocabularyUseCase
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

    factory { CountriesViewModel(get(), get()) as ICountriesViewModel }

    factory { ListsViewModel(get()) as IListsViewModel }
    factory { CoroutinesViewModel(get()) as ICoroutinesViewModel }
}