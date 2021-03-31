package org.cognizant.tms.service.exception;

public final class SubTasksNotFinishedException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public SubTasksNotFinishedException() {
        super();
    }

    public SubTasksNotFinishedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public SubTasksNotFinishedException(final String message) {
        super(message);
    }

    public SubTasksNotFinishedException(final Throwable cause) {
        super(cause);
    }

}