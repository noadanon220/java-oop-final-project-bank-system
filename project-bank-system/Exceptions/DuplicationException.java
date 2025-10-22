package Exceptions;

public class DuplicationException extends Exception {
    private final int duplicateAccountNumber;

    public DuplicationException(int duplicateAccountNumber) {
        super("Account number " + duplicateAccountNumber + " already exists.");
        this.duplicateAccountNumber = duplicateAccountNumber;
    }

    public int getDuplicateAccountNumber() {
        return duplicateAccountNumber;
    }
}
