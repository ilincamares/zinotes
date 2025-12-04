package com.zinotes.zinotesserver.repository

import com.zinotes.zinotesserver.model.Hanzi
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HanziRepository: JpaRepository<Hanzi, Long> {

}