package me.bytebeats.polyglot.dict.action

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.application.ex.ApplicationManagerEx
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.util.TextRange
import me.bytebeats.polyglot.dict.AbstractDictionary
import me.bytebeats.polyglot.dict.DictConsultListener
import me.bytebeats.polyglot.dict.meta.YouDaoTranslation
import me.bytebeats.polyglot.ui.PolyglotSettingState
import me.bytebeats.polyglot.util.LogUtils
import me.bytebeats.polyglot.util.StringResUtils

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2020/11/1 16:09
 * @Version 1.0
 * @Description ConsultDictAction is AnAction to offer consulting-a-dictionary service
 */

class ConsultDictAction :
        AnAction("Polyglot Translate", "Offered by powerful Polyglot dictionaries", AllIcons.Actions.Annotate) {
    private var lastActionPerformedTime = 0L
    override fun actionPerformed(e: AnActionEvent) {
        if (isFastClick()) {
            return
        }
        doConsultDict(e)
    }

    private fun doConsultDict(e: AnActionEvent) {
        val editor = e.getData(PlatformDataKeys.EDITOR)
        editor?.let {
            val model = it.selectionModel
            var selectedText = model.selectedText
            if (selectedText.isNullOrEmpty()) {
                selectedText = getCurrentWords(it)
                if (selectedText.isNullOrEmpty()) {
                    return
                }
            }
            val query = convertText2Query(selectedText)
            val desc = PolyglotSettingState.getInstance().dict
            AbstractDictionary.newInstance(desc, object : DictConsultListener {
                override fun onSuccess(translation: YouDaoTranslation) {
                    popupBalloon(editor, translation.format())
                }

                override fun onFailure(message: String) {
                    popupBalloon(editor, message)
                }

                override fun onError(error: String) {
                    popupBalloon(editor, error)
                }
            }).consult(query)
        }
    }

    private fun getCurrentWords(editor: Editor): String {
        val document = editor.document
        val caretModel = editor.caretModel
        val caretOffset = caretModel.offset
        val lineNo = document.getLineNumber(caretOffset)
        val startOffset = document.getLineStartOffset(lineNo)
        val endOffset = document.getLineEndOffset(lineNo)
        val lineContent = document.getText(TextRange.from(startOffset, endOffset))
        var start = 0
        var end = 0
        val cursor = caretOffset - startOffset
        if (!lineContent[cursor].isLetter()) {
            LogUtils.info("Caret not in word")
            return ""
        }
        for (ptr in cursor downTo 0) {
            if (!lineContent[ptr].isLetter()) {
                start = ptr + 1
                break
            }
        }
        var lastLetterPos = 0
        for (ptr in cursor until endOffset - startOffset) {
            lastLetterPos = ptr
            if (!lineContent[ptr].isLetter()) {
                end = ptr
                break
            }
        }
        if (end == 0) {
            end = lastLetterPos + 1
        }
        return lineContent.substring(start, end - start)
    }

    private fun convertText2Query(text: String): String {
        if (text.isNotEmpty()) {
            return text.replace('_', ' ')
                .replace("([A-Z]+)".toRegex(), " $0")
                .replace("/\\*+".toRegex(), "")
                .replace("\\*+/".toRegex(), "")
                .replace("\\*".toRegex(), "")
                .replace("//+".toRegex(), "")
                .replace("\t\n".toRegex(), " ")
                .replace("\\s+".toRegex(), " ")
        }
        return text
    }

    private fun isFastClick(timeSpan: Long = ACTION_TIME_SPAN): Boolean {
        val nowInMillis = System.currentTimeMillis()
        if (nowInMillis - lastActionPerformedTime < timeSpan) {
            return true
        }
        lastActionPerformedTime = nowInMillis
        return false
    }

    private fun popupBalloon(editor: Editor, text: String) {
        ApplicationManagerEx.getApplicationEx().invokeLater {
            JBPopupFactory.getInstance().apply {
                createHtmlTextBalloonBuilder(
                    text,
                    AllIcons.Actions.Annotate,
                    editor.colorsScheme.defaultBackground,
                    null
                )
                    .createBalloon()
                    .show(guessBestPopupLocation(editor), Balloon.Position.below)
            }
        }
    }

    companion object {
        const val ACTION_TIME_SPAN = 500L
    }
}