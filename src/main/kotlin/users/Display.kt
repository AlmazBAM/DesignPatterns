package users

import observer.Observer
import java.awt.Dimension
import java.awt.Font
import java.awt.Insets
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTextArea

class Display {

    fun show() {
        val text = JTextArea().apply {
            isEditable = false
            font = Font(Font.SANS_SERIF, Font.PLAIN, 16)
            margin = Insets(32, 32, 32, 32)
        }
        val scrollPane = JScrollPane(text)

        JFrame().apply {
            isVisible = true
            size = Dimension(600, 600)
            isResizable = false
            add(scrollPane)
        }
        UserRepository.getInstance("qwerty").addObservers(object : Observer<List<User>> {
            override fun onChanged(newValue: List<User>) {
                newValue
                    .joinToString("\n")
                    .let { text.text = it }
            }
        })
    }
}