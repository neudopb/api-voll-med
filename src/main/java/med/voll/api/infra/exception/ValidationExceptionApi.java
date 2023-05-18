package med.voll.api.infra.exception;

public class ValidationExceptionApi extends RuntimeException {
    public ValidationExceptionApi(String message) {
        super(message);
    }

    public ValidationExceptionApi(String message, Throwable cause) {
        super(message, cause);
    }
}
