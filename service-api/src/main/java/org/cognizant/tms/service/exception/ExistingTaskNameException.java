package org.cognizant.tms.service.exception;


public final class ExistingTaskNameException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public ExistingTaskNameException() {
        super();
    }

    public ExistingTaskNameException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ExistingTaskNameException(final String message) {
        super(message);
    }

    public ExistingTaskNameException(final Throwable cause) {
        super(cause);
    }

}