package com.tormenteddan.schooldemo.domain

import java.time.Year

data class GradingForm(val student: String,
                       val partial: Int,
                       val year: Int = Year.now().value,
                       val spanish: Int = 0,
                       val math: Int = 0,
                       val natSc: Int = 0,
                       val socialSc: Int = 0,
                       val pE: Int = 0,
                       val english: Int = 0) : Iterable<Pair<Subject, Int>> {
    /**
     * Returns an iterator over the elements of this object.
     */
    override fun iterator(): Iterator<Pair<Subject, Int>> {
        return Subject.values()
                .zip(listOf(spanish, math, natSc, socialSc, pE, english)).listIterator()
    }

}