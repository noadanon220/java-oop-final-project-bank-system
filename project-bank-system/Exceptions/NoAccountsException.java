package Exceptions;

public class NoAccountsException extends Exception {

    // Constructor with a default message
    public NoAccountsException() {
        super("No accounts available to display.");
    }
}
