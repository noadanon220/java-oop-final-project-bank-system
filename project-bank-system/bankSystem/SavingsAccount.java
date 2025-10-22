package bankSystem;

public class SavingsAccount extends Account {
    private double depositAmount;
    private int years;


    public SavingsAccount(int accountNumber, BankNumber bankNumber, String managerName, double depositAmount, int years) {
        super(accountNumber, bankNumber, managerName);
        this.depositAmount = depositAmount;
        this.years = years;
    }

    @Override
    public double calculateProfit() {
        return 0; // Savings accounts do not generate direct profit
    }

    @Override
    public String getAccountType() {
        return "Savings Account";
    }

    @Override
    public boolean isProfitRelevant() {
        return false;
    }
}
