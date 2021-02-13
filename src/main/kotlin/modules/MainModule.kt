package modules

import MainViewModel
import org.koin.dsl.module

val mainModule = module {
    factory { MainViewModel() }
}