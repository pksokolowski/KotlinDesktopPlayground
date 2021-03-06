package views

import androidx.compose.ui.text.input.TextFieldValue

val TextFieldValue.unselectedLength
    get() = text.length - (selection.end - selection.start)
