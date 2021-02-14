package modules

import features.mainScreen.IMainViewModel
import features.mainScreen.MainViewModel
import features.secondScreen.ISecondViewModel
import features.secondScreen.SecondViewModel
import org.koin.dsl.module

val mainModule = module {
    factory { MainViewModel() as IMainViewModel }
    factory { SecondViewModel() as ISecondViewModel }
}