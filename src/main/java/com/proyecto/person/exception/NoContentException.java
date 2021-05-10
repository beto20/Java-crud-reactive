package com.proyecto.person.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoContentException extends RuntimeException {

    public NoContentException(String message) {
        super(message);
    }
}