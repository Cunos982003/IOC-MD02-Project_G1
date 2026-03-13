package ra.coursemanagement.exception;

public class MyUncheckedException extends RuntimeException{
    public MyUncheckedException() {
    }
    public MyUncheckedException(String message) {
        super(message);
    }

    public MyUncheckedException(String message, Throwable cause) {
        super(message, cause);
    }
}
