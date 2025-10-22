package bankSystem;

public abstract class CheckingAccount extends Account {
    protected double credit;
    private final int INITIAL_REGULAR_CREDIT = 8000;

    public CheckingAccount(int accountNumber, BankNumber bankNumber, String managerName, double credit) {
        super(accountNumber, bankNumber, managerName);
        this.credit = credit;
    }

    public double getCredit() {
        return credit;
    }
}
