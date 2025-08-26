package users

import java.awt.Dimension
import java.awt.Font
import java.awt.Insets
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTextArea

class Display {
    private val text = JTextArea().apply {
        isEditable = false
        font = Font(Font.SANS_SERIF, Font.PLAIN, 16)
        margin = Insets(32, 32, 32, 32)
    }

    fun show() {
        val scrollPane = JScrollPane(text)

        JFrame().apply {
            isVisible = true
            size = Dimension(600, 600)
            isResizable = false
            add(scrollPane)
        }
        UserRepository.getInstance("qwerty").addObservers(this)
    }

    fun onChanged(users: List<User>) {
        users
            .joinToString("\n")
            .let { text.text = it }
    }
}