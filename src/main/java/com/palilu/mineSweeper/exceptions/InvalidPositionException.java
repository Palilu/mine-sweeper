package com.palilu.mineSweeper.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPositionException extends RuntimeException {

    public InvalidPositionException(String message, Object... args) {
        super(new MessageFormat(message).format(args));
    }
}
