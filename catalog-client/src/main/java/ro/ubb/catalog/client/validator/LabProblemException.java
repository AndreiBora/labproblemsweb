package ro.ubb.catalog.client.validator;

public class LabProblemException extends RuntimeException {
    public LabProblemException(String message) {
        super(message);
    }

    public LabProblemException(String message, Throwable cause) {
        super(message, cause);
    }

    public LabProblemException(Throwable cause) {
        super(cause);
    }
}
