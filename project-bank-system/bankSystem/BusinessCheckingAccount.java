package bankSystem;

public class BusinessCheckingAccount extends CheckingAccount implements FeeChargeable {
    private double businessRevenue;
    private final int COMMISSION = 3000;
    private final int BUSINESS_MANAGEMENT_FEE = 1000;


    // Constructor
    public BusinessCheckingAccount(int accountNumber, BankNumber bankNumber, String managerName, double credit, double businessRevenue) {
        super(accountNumber, bankNumber, managerName, credit);
        this.businessRevenue = businessRevenue;
    }

    @Override
    public String getAccountType() {
        return "Business Checking Account";
    }

    @Override
    public String displayAccountDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Account Type: ").append(getAccountType()).append("\n");
        details.append("Account Number: ").append(accountNumber).append("\n");
        details.append("Bank Number: ").append(bankNumber.name()).append(" (").append(bankNumber.getBankNumber()).append(")\n");
        details.append("Open Date: ").append(openDate).append("\n");
        details.append("Balance: ").append(Utils.formatCurrency(balance)).append("\n");
        details.append("Manager Name: ").append(managerName).append("\n");
        details.append("Business Revenue: ").append(Utils.formatCurrency(businessRevenue)).append("\n");
        details.append("Clients:\n");
        for (int i = 0; i < clientCount; i++) {
            details.append(" - ").append(clients[i]).append("\n");
        }
        return details.toString();
    }


    @Override
    public double calculateProfit() {
        return isVIP() ? 0 : credit * RATE_DIFFERENCE + COMMISSION;
    }


    public boolean isVIP() {
        if (businessRevenue >= 10000000) {
            for (Client client : getClients()) {
                if (client.getRank() != 10) return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isProfitRelevant() {
        return true;
    }


    @Override
    public double getManagementFee() {
        return isVIP() ? 0 : BUSINESS_MANAGEMENT_FEE; // Management fee for non-VIP
    }

    // Implementing Cloneable
    @Override
    protected BusinessCheckingAccount clone() {
        try {
            // Create a new instance of BusinessCheckingAccount
            BusinessCheckingAccount clonedAccount = new BusinessCheckingAccount(
                    this.accountNumber,
                    this.bankNumber,
                    this.managerName,
                    this.credit,
                    this.businessRevenue
            );

            // Deep copy the clients array
            if (this.clients != null) {
                Client[] clonedClients = new Client[this.clientCount];
                for (int i = 0; i < this.clientCount; i++) {
                    // Clone each client individually
                    clonedClients[i] = new Client(this.clients[i].getName(), this.clients[i].getRank());
                }
                clonedAccount.clients = clonedClients;
                clonedAccount.clientCount = this.clientCount;
            }

            return clonedAccount;

        } catch (Exception e) {
            throw new RuntimeException("An error occurred while cloning the account.", e);
        }
    }

}
