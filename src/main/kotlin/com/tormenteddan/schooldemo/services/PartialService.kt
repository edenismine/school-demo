package com.tormenteddan.schooldemo.services

import com.tormenteddan.schooldemo.domain.Partial
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PartialService {
    fun findAll() : List<Partial>{
        return  Partial.values().asList()
    }

    fun findCurrentPartial() : Partial{
        return Partial.values().first {
            LocalDateTime.now().month in it.months
        }
    }

    fun findPartial(ordinal : Int?) : Partial{
        return if(ordinal != null){
            Partial.values().firstOrNull {
                it.ordinal == ordinal
            } ?: findCurrentPartial()
        } else {
            findCurrentPartial()
        }
    }
}