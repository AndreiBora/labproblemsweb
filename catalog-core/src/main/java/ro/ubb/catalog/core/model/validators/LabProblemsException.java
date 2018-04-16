package ro.ubb.catalog.core.model.validators;

public class LabProblemsException extends RuntimeException {
    public LabProblemsException(String message) {
        super(message);
    }

    public LabProblemsException(String message, Throwable cause) {
        super(message, cause);
    }

    public LabProblemsException(Throwable cause) {
        super(cause);
    }
}
