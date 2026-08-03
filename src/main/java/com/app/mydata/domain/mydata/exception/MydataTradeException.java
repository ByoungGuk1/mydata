package com.app.mydata.domain.mydata.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MydataTradeException extends RuntimeException {

    public MydataTradeException(String message) {
        super(message);
    }
}
