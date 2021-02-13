package modules

import IMainViewModel
import MainViewModel
import org.koin.dsl.module

val mainModule = module {
    factory { MainViewModel() as IMainViewModel }
}