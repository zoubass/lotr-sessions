package com.zoubelu.fartmeter.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "fart_records")
class FartRecord(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val person: String = "",

    @Column(nullable = false)
    val rating: Int = 0,

    @Column(nullable = false)
    val timestamp: LocalDateTime = LocalDateTime.now()
)
