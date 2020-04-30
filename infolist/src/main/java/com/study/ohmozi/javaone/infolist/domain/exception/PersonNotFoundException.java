package com.study.ohmozi.javaone.infolist.domain.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Person Entity is undefined";

    public PersonNotFoundException(){
        super(MESSAGE);
        log.error(MESSAGE);
    }
}
