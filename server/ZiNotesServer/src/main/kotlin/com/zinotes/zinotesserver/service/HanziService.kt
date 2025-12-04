package com.zinotes.zinotesserver.service

import com.zinotes.zinotesserver.model.Hanzi
import com.zinotes.zinotesserver.repository.HanziRepository
import org.springframework.stereotype.Service

@Service
class HanziService(private val hanziRepository: HanziRepository) {
    fun getAllHanzi(): List<Hanzi> {
        return hanziRepository.findAll()
    }

    fun getHanziById(id: Long): Hanzi? {
        return hanziRepository.findById(id).orElse(null)
    }

    fun addHanzi(hanzi: Hanzi): Hanzi {
        return hanziRepository.save(hanzi)
    }

    fun updateHanzi(hanzi: Hanzi): Hanzi {
        return hanziRepository.save(hanzi)
    }

    fun deleteHanzi(id: Long) {
        hanziRepository.deleteById(id)
    }
}