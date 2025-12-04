package com.zinotes.zinotesserver.controller

import com.zinotes.zinotesserver.model.Hanzi
import com.zinotes.zinotesserver.service.HanziService
import com.zinotes.zinotesserver.websocket.SocketHandler
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.slf4j.LoggerFactory

@RestController
@RequestMapping("/api/hanzi")
class HanziController(
    private val hanziService: HanziService,
    private val socketHandler: SocketHandler
) {
    private val logger = LoggerFactory.getLogger(HanziController::class.java)

    @GetMapping
    fun getAll(): List<Hanzi> {
        logger.info("Request: GET all Hanzi")
        return hanziService.getAllHanzi()
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): Hanzi? {
        logger.info("Request: GET Hanzi ID: $id")
        return hanziService.getHanziById(id)
    }

    @PostMapping
    fun add(@RequestBody hanzi: Hanzi): Hanzi {
        logger.info("Request: ADD Hanzi -> Pinyin: ${hanzi.pinyin}")
        val saved = hanziService.addHanzi(hanzi)

        logger.info("Broadcasting update signal...")
        socketHandler.broadcast("update")
        return saved
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody hanzi: Hanzi): Hanzi {
        logger.info("Request: UPDATE Hanzi ID: $id")
        val updated = hanziService.updateHanzi(hanzi.copy(id = id))
        logger.info("Broadcasting update signal...")
        socketHandler.broadcast("update")
        return updated
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        logger.info("Request: DELETE Hanzi ID: $id")
        hanziService.deleteHanzi(id)
        logger.info("Broadcasting update signal...")
        socketHandler.broadcast("update")
    }

}