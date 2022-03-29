package com.my.photogram.validation;

public class UsernameExistException extends Throwable {

    public UsernameExistException(final String message) {
        super(message);
    }
}
