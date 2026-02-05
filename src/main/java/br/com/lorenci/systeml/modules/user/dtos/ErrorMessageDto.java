package br.com.lorenci.systeml.modules.user.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessageDto {

    private String message;
    private String field;
}
