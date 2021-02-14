package modules

import features.mainScreen.IMainViewModel
import features.mainScreen.MainViewModel
import org.koin.dsl.module

val mainModule = module {
    factory { MainViewModel() as IMainViewModel }
}