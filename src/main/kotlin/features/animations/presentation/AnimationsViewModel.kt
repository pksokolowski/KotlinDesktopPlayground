package features.animations.presentation

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import navigation.NavDestination
import navigation.Navigator

class AnimationsViewModel(
    private val navigator: Navigator
) : IAnimationsViewModel {
    private val animationsScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override val crossFadeContentVisible = MutableStateFlow(true)
    override val stateAnimationSlidePercentage = MutableStateFlow(0)
    override val autoAnimationSlideIsRightMost = MutableStateFlow(false)

    override fun toggleCrossFadeContentVisibility() {
        crossFadeContentVisible.value = !crossFadeContentVisible.value
    }

    override fun startStateAnimationSlide() {
        animationsScope.coroutineContext.cancelChildren()
        val initialState = stateAnimationSlidePercentage.value

        animationsScope.launch {
            if (initialState < 50) {
                for (i in initialState..100) {
                    delay(16)
                    stateAnimationSlidePercentage.value = i
                }
            } else {
                for (i in initialState downTo 0) {
                    delay(16)
                    stateAnimationSlidePercentage.value = i
                }
            }
        }
    }

    override fun toggleAutoAnimationSlide() {
        autoAnimationSlideIsRightMost.value = !autoAnimationSlideIsRightMost.value
    }

    override fun goBack() {
        animationsScope.coroutineContext.cancelChildren()
        navigator.navigateTo(NavDestination.Previous)
    }
}