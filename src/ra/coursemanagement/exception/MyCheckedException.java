package ra.coursemanagement.exception;


import java.sql.SQLException;

public class MyCheckedException extends Exception{

    public MyCheckedException(String message, Exception e) {
        super(message, e);
    }
    public MyCheckedException(String message, SQLException e) {
        super(message);
    }
}
