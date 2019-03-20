package nl.martijn1279.twdata.controller.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class RequestLogFilter : OncePerRequestFilter() {

    val excludedList = listOf("swagger", "vendor", "subscriptions", "favicon.ico")

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        if (!request.servletPath.containsList(excludedList)) {
            val requestString = "${request.getHeader("x-forwarded-for")} => ${request.servletPath} (${request.method}) ${ObjectMapper().writeValueAsString(request.parameterMap.filter { it.key != "callback" })} "
            LoggerFactory.getLogger("RequestLogFilter").info(requestString)
        }
        chain.doFilter(request, response)
    }

    private fun String.containsList(excludeList: List<String>): Boolean {
        excludeList.forEach {
            if (this.contains(it)) return true
        }
        return false
    }

}