package com.example.zinotes.data

import com.example.zinotes.model.Hanzi


object DataSource {
    val hanziList = listOf<Hanzi>(
        Hanzi(
            "zi", listOf(4, 0), 39, 6, 1,
            listOf("character", "letter", "word")
        ),
        Hanzi(
            "cha", listOf(2), 140, 9, 1,
            listOf("tea")
        )
    )
}