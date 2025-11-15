package org.vortxyz.pedido.service.application.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.vortxyz.application.handler.ErroDTO;
import org.vortxyz.application.handler.GlobalExceptionHandler;
import org.vortxyz.pedido.domain.exception.PedidoDomainException;
import org.vortxyz.pedido.domain.exception.PedidoNaoEncontradoException;

@Slf4j
@ControllerAdvice
public class PedidoGlobalExceptionHandler extends GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {PedidoDomainException.class})
    public ErroDTO handleException(PedidoDomainException pedidoDomainException) {
        log.error(pedidoDomainException.getMessage(), pedidoDomainException);
        return ErroDTO.builder().codigo(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .mensagem(pedidoDomainException.getMessage())
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {PedidoNaoEncontradoException.class})
    public ErroDTO handleNaoEncontradoException(PedidoNaoEncontradoException pedidoNaoEncontradoException) {
        log.error(pedidoNaoEncontradoException.getMessage(), pedidoNaoEncontradoException);
        return ErroDTO.builder().codigo(HttpStatus.NOT_FOUND.getReasonPhrase())
                .mensagem(pedidoNaoEncontradoException.getMessage())
                .build();
    }
}