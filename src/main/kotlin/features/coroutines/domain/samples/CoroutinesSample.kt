package features.coroutines.domain.samples

import kotlinx.coroutines.CoroutineScope

/**
 * A superclass for coroutine samples.
 *
 * @param command a unique command string, this can be used by user to trigger the command using text input
 * @param explanation a textual, preferably multiline, explanation of the sample, its behavior and reasons
 * behind it.
 */
abstract class CoroutinesSample(val command: String, val explanation: String) {
    /**
     * Runs the sample.
     *
     * @param scope a coroutine scope to use for the sample, if any scope is needed.
     * @param args a list of arguments provided by user.
     * @param output a function displaying the sample's outputs to the user.
     */
    abstract fun start(scope: CoroutineScope, args: List<String>, output: (String) -> Unit)
}
