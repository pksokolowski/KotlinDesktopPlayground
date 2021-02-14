package modules

import features.mainScreen.IMainViewModel
import features.mainScreen.MainViewModel
import features.secondScreen.ISecondViewModel
import features.secondScreen.SecondViewModel
import navigation.Navigator
import org.koin.dsl.module

val mainModule = module {
    single { Navigator() }
    factory { MainViewModel(get()) as IMainViewModel }
    factory { SecondViewModel(get()) as ISecondViewModel }
}