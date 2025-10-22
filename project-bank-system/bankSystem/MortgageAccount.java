package bankSystem;


public class MortgageAccount extends Account implements FeeChargeable{
    private double originalMortgageAmount;
    private double monthlyPayment;
    private int years;

    public MortgageAccount(int accountNumber, BankNumber bankNumber, String managerName, double originalMortgageAmount, double monthlyPayment, int years) {
        super(accountNumber, bankNumber, managerName);
        this.originalMortgageAmount = originalMortgageAmount;
        this.monthlyPayment = monthlyPayment;
        this.years = years;
    }

    @Override
    public double calculateProfit() {
        return ((0.8 * originalMortgageAmount) / years) * RATE_DIFFERENCE;
    }

    @Override
    public String getAccountType() {
        return "Mortgage Account";
    }

    @Override
    public boolean isProfitRelevant() {
        return true;
    }

    @Override
    public double getManagementFee() {
        return 0.1 * originalMortgageAmount; // 0.1% of the original mortgage amount
    }
}
