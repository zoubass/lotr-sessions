package com.zoubelu.fartmeter.repository

import com.zoubelu.fartmeter.entity.FartRecord
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface FartRecordRepository : JpaRepository<FartRecord, Long> {

    fun countByPerson(person: String): Long

    fun findTop10ByOrderByTimestampDesc(): List<FartRecord>

    fun findTopByOrderByTimestampDesc(): FartRecord?

    @Query("SELECT AVG(f.rating) FROM FartRecord f WHERE f.person = :person")
    fun averageRatingByPerson(person: String): Double?
}
