package nl.martijn1279.twdata.controller.util

import com.fasterxml.jackson.databind.ObjectMapper
import io.micrometer.core.instrument.Metrics
import nl.martijn1279.twdata.data.UserRepository
import nl.martijn1279.twdata.data.Users
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.sql.Timestamp
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class RequestLogFilter : OncePerRequestFilter() {

    @Autowired
    lateinit var userRepository: UserRepository

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        if (request.servletPath != "/favicon.ico" && !request.servletPath.contains("swagger")) {
            val requestString = "${request.remoteAddr} => ${request.servletPath} (${request.method}) ${ObjectMapper().writeValueAsString(request.parameterMap.filter { it.key != "callback" })} "
            LoggerFactory.getLogger("RequestLogFilter").info(requestString)
            userRepository.findUser(request.remoteAddr)
                    ?.let {
                        userRepository.update(it
                                .also { user ->
                                    user.lastRequest = Timestamp(Date().time)
                                    user.requestCounter++
                                })
                    }
                    ?: userRepository.save(Users(UUID.randomUUID().toString(), request.remoteAddr, Timestamp(Date().time), Timestamp(Date().time))).let {
                        val counter = Metrics.counter("uniqueVisitors")
                        if (counter.count() == 0.0) counter.increment(userRepository.count().toDouble())
                        counter.increment()
                    }
        }
        chain.doFilter(request, response)
    }
}