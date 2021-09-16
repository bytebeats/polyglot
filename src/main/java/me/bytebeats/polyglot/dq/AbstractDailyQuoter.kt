package me.bytebeats.polyglot.dq

import me.bytebeats.polyglot.dq.impl.IcibaDailyQuoter
import me.bytebeats.polyglot.dq.impl.ScallopDailyQuoter
import me.bytebeats.polyglot.dq.impl.YoudaoDailyQuoter
import me.bytebeats.polyglot.http.DailyQuoteConnectionCloser
import me.bytebeats.polyglot.http.IDailyQuote
import me.bytebeats.polyglot.http.ParamsAdder
import me.bytebeats.polyglot.meta.DailyQuote
import me.bytebeats.polyglot.util.LogUtils
import me.bytebeats.polyglot.util.StringResUtils
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2020/9/18 20:17
 * @Version 1.0
 * @Description AbstractDailyQuoter offers quote every day
 */

abstract class AbstractDailyQuoter(url: String) : DailyQuoteConnectionCloser(url = url), IDailyQuote, ParamsAdder {

    /**
     * Execute the translation or TTS task (send a POST or GET request to the server),
     * receive the result of translation or speech conversion., and return the content
     * or save file name as string.
     *
     * @return the string form of the translated result.
     * @throws Exception if the request fails
     */
    @Throws(Exception::class)
    abstract fun query(): String

    /**
     * Parse the string to extract the {@see #DailyQuote}.
     *
     * @param text the string form of the daily quote result.
     * @return DailyQuote results after parsing.
     * @throws IOException if the parsing fails.
     */
    @Throws(IOException::class)
    protected abstract fun parse(text: String?): DailyQuote?

    override fun quote(): DailyQuote? {
        var dailyQuote: DailyQuote? = null
        add()
        try {
            dailyQuote = parse(query())
        } catch (e: Exception) {
            LogUtils.info(e.message)
        } finally {
            close()
        }
        return dailyQuote
    }

    fun getToday(): String = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now())

    companion object Factory {
        fun newInstance(quoter: String): AbstractDailyQuoter =
            when (quoter) {
                StringResUtils.QUOTOR_SCALLOP, StringResUtils.QUOTOR_SCALLOP_EN -> ScallopDailyQuoter()
                StringResUtils.QUOTOR_ICIBA, StringResUtils.QUOTOR_ICIBA_EN -> IcibaDailyQuoter()
                StringResUtils.QUOTOR_YOUDAO, StringResUtils.QUOTOR_YOUDAO_EN -> YoudaoDailyQuoter()
                else -> YoudaoDailyQuoter()
            }

        fun newInstance(quoter: DailyQuoter): AbstractDailyQuoter =
            when (quoter) {
                DailyQuoter.SCALLOP -> ScallopDailyQuoter()
                DailyQuoter.ICIBA -> IcibaDailyQuoter()
                DailyQuoter.YOUDAO -> YoudaoDailyQuoter()
            }

//        @JvmStatic
//        fun main(args: Array<String>) {
//            println("扇贝")
//            val scallop = ScallopDailyQuoter()
//            val scallopQuoter = scallop.quote()
//            println("${scallopQuoter?.date} ${scallopQuoter?.content} ${scallopQuoter?.translation}")
//            println("Iciba")
//            val iciba = IcibaDailyQuoter()
//            val icibaQuoter = iciba.quote()
//            println("${icibaQuoter?.date} ${icibaQuoter?.content} ${icibaQuoter?.translation}")
//            println("Youdao")
//            val youdao = IcibaDailyQuoter()
//            val youdaoQuoter = youdao.quote()
//            println("${youdaoQuoter?.date} ${youdaoQuoter?.content} ${youdaoQuoter?.translation}")
//        }
    }
}