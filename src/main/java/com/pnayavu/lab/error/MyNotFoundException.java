package com.pnayavu.lab.error;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class MyNotFoundException extends ResponseStatusException {
    public MyNotFoundException(HttpStatusCode status) {
        super(status);
    }

    public MyNotFoundException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public MyNotFoundException(int rawStatusCode, String reason, Throwable cause) {
        super(rawStatusCode, reason, cause);
    }

    public MyNotFoundException(HttpStatusCode status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
    protected MyNotFoundException(HttpStatusCode status, String reason, Throwable cause, String messageDetailCode, Object[] messageDetailArguments) {
        super(status, reason, cause, messageDetailCode, messageDetailArguments);
    }
}
