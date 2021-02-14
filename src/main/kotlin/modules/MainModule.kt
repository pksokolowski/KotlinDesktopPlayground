package modules

import features.mainScreen.IMainViewModel
import features.mainScreen.MainViewModel
import features.typingSpeed.ITypingSpeedViewModel
import features.typingSpeed.TypingSpeedViewModel
import navigation.Navigator
import navigation.ScreenBackStack
import org.koin.dsl.module

val mainModule = module {
    single { Navigator() }
    factory { ScreenBackStack() }
    factory { MainViewModel(get()) as IMainViewModel }
    factory { TypingSpeedViewModel(get()) as ITypingSpeedViewModel }
}