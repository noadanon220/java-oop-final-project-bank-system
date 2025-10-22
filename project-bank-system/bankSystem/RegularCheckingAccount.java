package bankSystem;

public class RegularCheckingAccount extends CheckingAccount {

    public RegularCheckingAccount(int accountNumber, BankNumber bankNumber, String managerName, double credit) {
        super(accountNumber, bankNumber, managerName, credit);
    }

    @Override
    public double calculateProfit() {
        return credit * RATE_DIFFERENCE;
    }

    @Override
    public String getAccountType() {
        return "Regular Checking Account";
    }

    @Override
    public boolean isProfitRelevant() {
        return true;
    }
}
