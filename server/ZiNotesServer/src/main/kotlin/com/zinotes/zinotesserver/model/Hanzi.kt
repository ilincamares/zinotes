package com.zinotes.zinotesserver.model

import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "hanzi")
data class Hanzi (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val pinyin: String = "",

    @ElementCollection
    val tones: List<Int> = listOf(),

    val radicalNumber: Int? = null,

    val strokeCount: Int? = null,

    val hskLevel: Int? = null,

    @ElementCollection
    val definitions: List<String>? = null
)