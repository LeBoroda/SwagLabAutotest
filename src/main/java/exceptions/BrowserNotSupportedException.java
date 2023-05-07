package exceptions;

public class BrowserNotSupportedException extends Exception {
    public BrowserNotSupportedException(String browserName) {
        super(String.format("Browser %s is not supported", browserName));
    }

}
