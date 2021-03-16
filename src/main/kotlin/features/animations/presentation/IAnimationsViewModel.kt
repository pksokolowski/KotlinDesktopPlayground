package features.animations.presentation

import kotlinx.coroutines.flow.MutableStateFlow

interface IAnimationsViewModel {
    fun goBack()
    fun toggleCrossFadeContentVisibility()
    val crossFadeContentVisible: MutableStateFlow<Boolean>
}