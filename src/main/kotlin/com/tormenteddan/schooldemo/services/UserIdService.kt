package com.tormenteddan.schooldemo.services

import java.util.*
import kotlin.collections.HashSet
import kotlin.math.absoluteValue

object UserIdService {
    private val assigned = HashSet<String>()
    private fun uuid(): String {
        return (UUID.randomUUID().mostSignificantBits % 99999999)
                .toInt().absoluteValue
                .toString().padStart(8, '0')
    }

    fun next(): String {
        var next = uuid()
        while (next in assigned) {
            next = uuid()
        }
        assigned.add(next)
        return next
    }
}