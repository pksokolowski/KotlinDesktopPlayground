package features.coroutines.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import navigation.NavDestination
import navigation.Navigator

class CoroutinesViewModel(
    private val navigator: Navigator
) : ICoroutinesViewModel {
    private val coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override fun goBack() {
        coroutineScope.coroutineContext.cancelChildren()
        navigator.navigateTo(NavDestination.Previous)
    }
}