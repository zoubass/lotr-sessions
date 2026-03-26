package com.zoubelu.fartmeter.controller

import com.zoubelu.fartmeter.entity.FartRecord
import com.zoubelu.fartmeter.repository.FartRecordRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

data class FartRequest(val person: String, val rating: Int)

data class PersonStats(
    val person: String,
    val totalFarts: Long,
    val averageRating: Double
)

data class StatsResponse(val stats: List<PersonStats>)

@RestController
@RequestMapping("/api/farts")
class FartController(private val repository: FartRecordRepository) {

    companion object {
        val VALID_PEOPLE = listOf("Misak", "Ondra", "Pitek", "Zoubas")
    }

    @PostMapping
    fun recordFart(@RequestBody request: FartRequest): ResponseEntity<Any> {
        if (request.person !in VALID_PEOPLE) {
            return ResponseEntity.badRequest()
                .body(mapOf("error" to "Invalid person. Must be one of: $VALID_PEOPLE"))
        }
        if (request.rating !in 1..5) {
            return ResponseEntity.badRequest()
                .body(mapOf("error" to "Rating must be between 1 and 5"))
        }

        val record = FartRecord(
            person = request.person,
            rating = request.rating,
            timestamp = LocalDateTime.now()
        )
        val saved = repository.save(record)
        return ResponseEntity.ok(saved)
    }

    @GetMapping("/stats")
    fun getStats(): StatsResponse {
        val stats = VALID_PEOPLE.map { person ->
            PersonStats(
                person = person,
                totalFarts = repository.countByPerson(person),
                averageRating = repository.averageRatingByPerson(person) ?: 0.0
            )
        }
        return StatsResponse(stats = stats)
    }

    @GetMapping("/recent")
    fun getRecent(): List<FartRecord> {
        return repository.findTop10ByOrderByTimestampDesc()
    }

    @DeleteMapping("/last")
    fun deleteLast(): ResponseEntity<Any> {
        val last = repository.findTopByOrderByTimestampDesc()
            ?: return ResponseEntity.notFound().build()
        repository.delete(last)
        return ResponseEntity.ok(mapOf("deleted" to last.id))
    }

    @DeleteMapping
    fun deleteAll(): ResponseEntity<Any> {
        val count = repository.count()
        repository.deleteAll()
        return ResponseEntity.ok(mapOf("deleted" to count))
    }
}
