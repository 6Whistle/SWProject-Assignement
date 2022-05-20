package com.junhwei.board;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

//error Handling
public class InfoNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(InfoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String InfoNotFoundHandler(InfoNotFoundException e){ return e.getMessage(); }
}
