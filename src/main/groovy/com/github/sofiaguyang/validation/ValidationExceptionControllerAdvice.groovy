package com.github.sofiaguyang.validation

import com.github.sofiaguyang.hateoas.ErrorResource
import com.github.sofiaguyang.hateoas.FieldErrorResource
import com.github.sofiaguyang.validation.ValidationService.ValidationException
import org.springframework.context.MessageSource
import org.springframework.http.ResponseEntity
import org.springframework.util.Assert
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

import java.util.function.Supplier

import static org.springframework.http.HttpStatus.BAD_REQUEST

@ControllerAdvice
class ValidationExceptionControllerAdvice {

    Supplier<String> logrefSupplier

    MessageSource messageSource

    String code = '000400'

    String message = 'Request contains invalid fields!'

    ValidationExceptionControllerAdvice(Supplier<String> logrefSupplier, MessageSource messageSource) {
        Assert.notNull(logrefSupplier, "Logref supplier required!")
        Assert.notNull(messageSource, "Message source required!")
        this.logrefSupplier = logrefSupplier
        this.messageSource = messageSource
    }

    @ExceptionHandler(ValidationException)
    protected ResponseEntity<ErrorResource> handle(ValidationException ex) {
        def errorResource = new ErrorResource(
                logref: logrefSupplier.get(),
                code: code,
                message: message,
                total: ex.errors.fieldErrors.size()
        )
        errorResource.embedResource('errors', toResources(ex.errors.fieldErrors))

        return new ResponseEntity<ErrorResource>(errorResource, BAD_REQUEST)
    }

    private List<FieldErrorResource> toResources(List<FieldError> fieldErrors) {
        return fieldErrors.collect { FieldError fieldError ->
            def errorMessageCode = fieldError.codes.find { String code ->
                code.startsWith(fieldError.field)
            }

            return new FieldErrorResource(
                    field: fieldError.field,
                    code: errorMessageCode,
                    message: messageSource.getMessage(fieldError, null)
            )
        }
    }
}
