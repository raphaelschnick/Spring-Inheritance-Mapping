package de.sybit.spring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotExistsException extends RuntimeException {

    public EntityNotExistsException() {
        super();
    }

    public EntityNotExistsException(String message) {
        super(message);
    }

}
