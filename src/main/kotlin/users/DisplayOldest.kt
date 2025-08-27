package users

import java.awt.Dimension
import java.awt.Font
import java.awt.Insets
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTextArea

class DisplayOldest {

    fun show() {
        val text = JTextArea().apply {
            isEditable = false
            font = Font(Font.SANS_SERIF, Font.PLAIN, 16)
            margin = Insets(32, 32, 32, 32)
        }
        val scrollPane = JScrollPane(text)

        JFrame().apply {
            isVisible = true
            size = Dimension(700, 600)
            isResizable = false
            add(scrollPane)
        }
        UserRepository.getInstance("qwerty").oldestUser.addObserver { newValue ->
            text.text = "Oldest is: $newValue"
        }
    }
}