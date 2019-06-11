package io.capsulo.min808.core.utils

import java.util.*

/**
 * Used to format representation of date.
 */
class CalendarUtils {

    companion object {

        /**
         * Return an abbreviation of year's month from their representation's number.
         */
        fun getMonthLetter(day: Int): String = when(day) {
            Calendar.JANUARY -> "Jan"
            Calendar.FEBRUARY -> "Feb"
            Calendar.MARCH -> "Mar"
            Calendar.APRIL -> "Apr"
            Calendar.MAY -> "May"
            Calendar.JUNE -> "Jun"
            Calendar.JULY -> "July"
            Calendar.AUGUST -> "Aug"
            Calendar.SEPTEMBER -> "Sep"
            Calendar.OCTOBER -> "Oct"
            Calendar.NOVEMBER -> "Nov"
            Calendar.DECEMBER -> "Dec"
            else -> ""
        }


        fun getHumanRedableDate(timeInMillis: Long?): String {
            val SPACE = ' '

            return if(timeInMillis != null) {
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = timeInMillis
                calendar.get(Calendar.DAY_OF_MONTH).toString() + SPACE + getMonthLetter(calendar.get(Calendar.MONTH)) + SPACE + calendar.get(Calendar.YEAR)
            } else ""
        }
    }

}