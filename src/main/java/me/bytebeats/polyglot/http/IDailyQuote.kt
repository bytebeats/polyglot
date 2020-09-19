package me.bytebeats.polyglot.http

import me.bytebeats.polyglot.meta.DailyQuote

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2020/9/18 19:59
 * @Version 1.0
 * @Description IDailyQuote will execute a task to fetch today's quote over http.
 */

interface IDailyQuote {

    /**
     *  fetch today's quote
     * @param date today, in format of "yyyy-MM-dd"
     * @return today's quote.
     */
    fun quote(): DailyQuote?
}