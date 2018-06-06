package com.tormenteddan.schooldemo.domain

import java.time.Month

enum class Partial(val months: List<Month>) {
    JAN_FEB(listOf(Month.JANUARY, Month.FEBRUARY)),
    APR_MAY(listOf(Month.APRIL, Month.MAY)),
    JUN_JUL(listOf(Month.JUNE, Month.JULY)),
    AUG_SEP(listOf(Month.AUGUST, Month.SEPTEMBER)),
    OCT_DEC(listOf(Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER));

    override fun toString(): String {
        return when(this){
            JAN_FEB -> "Jan - Feb"
            APR_MAY -> "Apr - May"
            JUN_JUL -> "Jun - Jul"
            AUG_SEP -> "Aug - Sep"
            OCT_DEC -> "Oct - Dec"
        }
    }
}
