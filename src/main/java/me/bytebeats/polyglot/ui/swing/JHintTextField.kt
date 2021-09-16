package me.bytebeats.polyglot.ui.swing

import com.intellij.ui.JBColor
import java.awt.Font
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import javax.swing.JTextField

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/9/16 20:18
 * @Version 1.0
 * @Description JTextField with a hint
 */

class JHintTextField(private val hint: String?) : JTextField(), FocusListener {
    private var gainFont: Font? = null
    private var lostFont: Font? = null

    init {
        val font = font
        gainFont = Font(font.fontName, font.style, font.size)
        lostFont = Font(font.fontName, Font.ITALIC, font.size)
        text = hint
        setFont(lostFont)
        foreground = JBColor.GRAY
        super.addFocusListener(this)
    }

    override fun focusGained(e: FocusEvent?) {
        if (super.getText() == hint) {
            text = ""
            font = gainFont
        } else {
            text = super.getText()
            font = gainFont
        }
    }

    override fun focusLost(e: FocusEvent?) {
        if (super.getText() == hint || super.getText().isEmpty()) {
            super.setText(hint)
            font = lostFont
            foreground = JBColor.GRAY
        } else {
            text = super.getText()
            font = gainFont
            foreground = JBColor.BLACK
        }
    }

    override fun getText(): String? {
        return if (super.getText() == hint) {
            ""
        } else {
            super.getText()
        }
    }
}