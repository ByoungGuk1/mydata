package com.app.mydata.domain.mydata.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MydataRiaAccountException extends RuntimeException {

    public MydataRiaAccountException(String message) {
        super(message);
    }
}