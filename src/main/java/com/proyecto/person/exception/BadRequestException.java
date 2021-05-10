package com.proyecto.person.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor

public class BadRequestException extends RuntimeException{

    public BadRequestException(String message) {
        super(message);
    }


}
