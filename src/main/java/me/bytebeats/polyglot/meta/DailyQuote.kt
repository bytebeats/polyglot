package me.bytebeats.polyglot.meta

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2020/9/18 20:01
 * @Version 1.0
 * @Description DailyQuote is basic data structure
 */

class DailyQuote(val date: String, val content: String, val translation: String) {
    fun getMultilineContent(): String {
        val html = StringBuilder("<html>")
        for (i in content.indices) {//English
            if (i in 1..content.lastIndex && i % (MAX_LINE_WIDTH * 2) == 0) {
                html.append("<br>")
            }
            html.append(content[i])
        }
        html.append("</html>")
        return html.toString()
    }

    fun getMultilineTranslation(): String {
        val html = StringBuilder("<html>")
        for (i in translation.indices) {//Chinese
            if (i in 1..translation.lastIndex && i % MAX_LINE_WIDTH == 0) {
                html.append("<br>")
            }
            html.append(translation[i])
        }
        html.append("</html>")
        return html.toString()
    }

    companion object {
        private const val MAX_LINE_WIDTH = 30
    }
}