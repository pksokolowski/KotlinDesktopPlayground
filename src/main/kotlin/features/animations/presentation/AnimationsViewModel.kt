package features.animations.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import navigation.NavDestination
import navigation.Navigator

class AnimationsViewModel(
    private val navigator: Navigator
) : IAnimationsViewModel {
    private val coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override val crossFadeContentVisible = MutableStateFlow(true)

    override fun toggleCrossFadeContentVisibility() {
        crossFadeContentVisible.value = !crossFadeContentVisible.value
    }

    override fun goBack() {
        coroutineScope.coroutineContext.cancelChildren()
        navigator.navigateTo(NavDestination.Previous)
    }
}