package com.ens.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiErrorEvent {

    private Throwable throwable;
    private String message;

    public ApiErrorEvent(String message) {
        this.message = message;
    }

    public ApiErrorEvent(Throwable throwable, String message) {
        this.throwable = throwable;
        this.message = message;
    }

    public ApiErrorEvent(Throwable throwable) {
        this.throwable = throwable;
    }
}
