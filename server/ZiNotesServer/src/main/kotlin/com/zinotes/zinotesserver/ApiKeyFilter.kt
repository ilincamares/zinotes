package com.zinotes.zinotesserver

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ApiKeyFilter : Filter {

    @Value("\${APP_API_KEY")
    private lateinit var validApiKey: String

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse

        val path = httpRequest.requestURI

        if (path == "/" || path.startsWith("/error")) {
            chain.doFilter(request, response)
            return
        }

        val requestApiKey = httpRequest.getHeader("X-API-KEY")

        if (validApiKey == requestApiKey) {
            chain.doFilter(request, response)
        } else {
            httpResponse.status = HttpServletResponse.SC_UNAUTHORIZED
            httpResponse.writer.write("Invalid API Key")
            return
        }
    }
}