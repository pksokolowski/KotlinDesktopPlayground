package features.animations.presentation

import kotlinx.coroutines.flow.MutableStateFlow

interface IAnimationsViewModel {
    val crossFadeContentVisible: MutableStateFlow<Boolean>
    val stateAnimationSlidePercentage: MutableStateFlow<Int>
    val autoAnimationSlideIsRightMost: MutableStateFlow<Boolean>
    fun startStateAnimationSlide()
    fun toggleAutoAnimationSlide()
    fun toggleCrossFadeContentVisibility()
    fun goBack()
}