package Exceptions;

public class NotBusinessAccountException extends Exception {
  public NotBusinessAccountException(int accountNumber) {
    super("Account number " + accountNumber + " is not a business account.");
  }
}
