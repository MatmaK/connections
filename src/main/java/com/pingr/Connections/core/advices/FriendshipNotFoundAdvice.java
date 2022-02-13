package com.pingr.Connections.core.advices;

import com.pingr.Connections.core.exceptions.FriendshipNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FriendshipNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(FriendshipNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String illegalArgumentHandler(FriendshipNotFoundException ex) {
        return ex.getMessage();
    }
}
