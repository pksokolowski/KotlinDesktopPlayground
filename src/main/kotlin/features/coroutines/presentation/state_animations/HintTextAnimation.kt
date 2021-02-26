package features.coroutines.presentation.state_animations

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun CoroutineScope.animateTextHint(hint: String, output: (String) -> Unit) {
    launch {
        for (i in hint.indices) {
            output(hint.substring(0, i+1))
            delay(30)
        }
        delay(500)
        for(i in hint.length-1 downTo 0){
            output(hint.substring(0, i))
            delay(20)
        }
    }
}