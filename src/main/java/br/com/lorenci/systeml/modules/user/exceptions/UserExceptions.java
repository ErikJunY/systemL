package br.com.lorenci.systeml.modules.user.exceptions;


import br.com.lorenci.systeml.modules.user.dtos.ErrorMessageDto;
import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@ControllerAdvice //permite capturar exceções globalmente e retornar respostas de erro personalizadas sem sobrecarregar a lógica de negócios com tratamento de erros
public class UserExceptions {

    private final MessageSource messageSource;

    public UserExceptions(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDto>> handleArgumentNotValidException(MethodArgumentNotValidException ex){
        List<ErrorMessageDto> dto = new ArrayList<>();

        ex.getFieldErrors().forEach((fieldError) -> {
            String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            ErrorMessageDto error = new ErrorMessageDto(message, fieldError.getField());

            dto.add(error);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessageDto> handleDataIntegrityViolationException(DataIntegrityViolationException ex){
        String field = "";

        if (Objects.equals(ex.getMessage(), "CPF ja cadastrado")) {
            field = "cpf";
        } else if (Objects.equals(ex.getMessage(), "Email ja cadastrado")) {
            field = "email";
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessageDto(ex.getMessage(), field));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handleResourceNotFoundException(ResourceNotFoundException ex){
        String field = "";

        if (Objects.equals(ex.getMessage(), "CPF ja cadastrado")) {
            field = "cpf";
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessageDto(ex.getMessage(), field));
    }
}
