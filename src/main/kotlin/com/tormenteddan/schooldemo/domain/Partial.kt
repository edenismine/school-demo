package com.tormenteddan.schooldemo.domain

import java.time.Month

enum class Partial(val first: Month, val second: Month) {
    JAN_FEB(Month.JANUARY, Month.FEBRUARY),
    APR_MAY(Month.APRIL, Month.MAY),
    JUN_JUL(Month.JUNE, Month.JULY),
    AGO_SEP(Month.AUGUST, Month.SEPTEMBER),
    OCT_NOV(Month.OCTOBER, Month.NOVEMBER)
}
