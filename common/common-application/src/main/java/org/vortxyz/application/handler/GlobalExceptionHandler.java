package org.vortxyz.application.handler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroDTO handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ErroDTO.builder()
                .codigo(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .mensagem("Erro inesperado.")
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroDTO handleValidationException(ValidationException validationException) {
        String mensagem;
        if (validationException instanceof ConstraintViolationException) {
            mensagem = extrairViolacoesDaExcecao((ConstraintViolationException) validationException);
        } else {
            mensagem = validationException.getMessage();
        }
        log.error(mensagem, validationException);
        return ErroDTO.builder()
                .codigo(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .mensagem(mensagem)
                .build();
    }

    private String extrairViolacoesDaExcecao(ConstraintViolationException validationException) {
        return validationException.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(" - "));
    }
}
