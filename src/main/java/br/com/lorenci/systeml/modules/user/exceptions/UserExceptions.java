package br.com.lorenci.systeml.modules.user.exceptions;


import br.com.lorenci.systeml.modules.user.dtos.ErrorMessageDto;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice //permite capturar exceções globalmente e retornar respostas de erro personalizadas sem sobrecarregar a lógica de negócios com tratamento de erros
public class UserExceptions {

    private MessageSource messageSource;

    public UserExceptions(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDto>> handleArgumentNotValid(MethodArgumentNotValidException ex){
        List<ErrorMessageDto> dto = new ArrayList<>();

        ex.getFieldErrors().forEach((fieldError) -> {
            String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            ErrorMessageDto error = new ErrorMessageDto(message, fieldError.getField());

            dto.add(error);
        });

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
}
