package com.example.demo.core.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import com.example.demo.core.response.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(InvalidInputException::class)
    protected fun invalidInputException(ex: InvalidInputException): ResponseEntity<ErrorResponse> {
        val errors = ErrorResponse(ex.message?: "Not Exception Message")
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }
}