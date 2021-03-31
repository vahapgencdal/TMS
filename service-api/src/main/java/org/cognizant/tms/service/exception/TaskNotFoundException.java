package org.cognizant.tms.service.exception;


public final class TaskNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public TaskNotFoundException() {
        super();
    }

    public TaskNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TaskNotFoundException(final String message) {
        super(message);
    }

    public TaskNotFoundException(final Throwable cause) {
        super(cause);
    }

}