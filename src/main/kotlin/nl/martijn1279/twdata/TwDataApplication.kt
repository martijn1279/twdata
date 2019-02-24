package nl.martijn1279.twdata

import nl.martijn1279.twdata.controller.util.RequestLogFilter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling


@SpringBootApplication
@EnableScheduling
class TwDataApplication

fun main(args: Array<String>) {
    runApplication<TwDataApplication>(*args)
}

@Bean
fun servletRegistrationBean(): FilterRegistrationBean<RequestLogFilter> {
    val registrationBean = FilterRegistrationBean<RequestLogFilter>()
    registrationBean.filter = RequestLogFilter()
    registrationBean.order = 2
    return registrationBean
}