package com.ysyesilyurt.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class PotentialPlaylistRuntimeException extends RuntimeException {

    public PotentialPlaylistRuntimeException() {
        super();
    }

    public PotentialPlaylistRuntimeException(String message) {
        super(message);
    }

    public PotentialPlaylistRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
