package com.carcara.imagem_backend_security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratamentoErros {

    @ExceptionHandler(SolicitacaoNaoAutorizada.class)
    public ResponseEntity<RespostaErro> tratarErro404NoSuchElementException(SolicitacaoNaoAutorizada ex) {
        RespostaErro respostaErro = new RespostaErro(ex.getMsg(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respostaErro);
    }

}
