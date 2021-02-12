package modules

import feature.dependencies.Dependency
import org.koin.dsl.module

val mainModule = module {
    single { Dependency() }
}