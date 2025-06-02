package com.jakem.nettracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTickerException extends RuntimeException {

    public InvalidTickerException(String symbol) {
        super("Unknown or unsupported ticker: " + symbol);
    }
}
