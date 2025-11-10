package com.example.zinotes.data

import com.example.zinotes.model.Hanzi


object DataSource {
    var hanziList = mutableListOf<Hanzi>(
        Hanzi(
            "1","zi", listOf(4, 0), 39, 6, 1,
            listOf("character", "letter", "word")
        ),
        Hanzi(
            "2","cha", listOf(2), 140, 9, 1,
            listOf("tea")
        )
    )

    fun findHanziById(id: String): Hanzi? {
        return hanziList.find { it.id == id }
    }

    fun addHanzi(hanzi: Hanzi) {
        hanziList.add(hanzi)
    }

    fun updateHanzi(hanzi: Hanzi) {
        val index = hanziList.indexOfFirst { it.id == hanzi.id }
        if (index != -1) {
            hanziList[index] = hanzi
        }
        else
            addHanzi(hanzi)
    }

    fun deleteHanziById(hanziId: String?) {
        if (hanziId == null) return
        hanziList.removeAll { it.id == hanziId }
    }
}