package nl.martijn1279.twdata.controller.util

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice

@ControllerAdvice
class JsonpControllerAdvice : AbstractJsonpResponseBodyAdvice("callback")