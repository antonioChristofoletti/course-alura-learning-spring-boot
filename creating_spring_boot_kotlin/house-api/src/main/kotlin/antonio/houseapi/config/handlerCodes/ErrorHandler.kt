package antonio.houseapi.config.handlerCodes

import antonio.houseapi.dto.DtoError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorHandler(
    @Autowired val messageSource: MessageSource) {

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handler(manve: MethodArgumentNotValidException): List<DtoError> {
        return manve.bindingResult.fieldErrors.map {
            DtoError(
                HttpStatus.BAD_REQUEST.value(),
                "Field: ${it.field} - ${messageSource.getMessage(it, LocaleContextHolder.getLocale())}"
            )
        }
    }
}