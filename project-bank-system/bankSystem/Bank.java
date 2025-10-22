package bankSystem;

import Exceptions.DuplicationException;
import Exceptions.NoAccountsException;
import Exceptions.NotBusinessAccountException;

import java.util.Arrays;

public class Bank {
    private Account[] accounts;
    private int accountCount;
    private static final int INITIAL_SIZE = 2;
    protected int nextAccountNumber; // Global variable to keep track of the next account number for automatic generation
    private double ceoBonus; // Total management fees collected as CEO bonus

    // Constructor
    public Bank() {
        accounts = new Account[INITIAL_SIZE];
        accountCount = 0;
        nextAccountNumber = 1000;
        ceoBonus = 0; // Initialize CEO bonus
    }

    // Method to add an account to the system
    public void addAccount(Account account) throws DuplicationException {
        // Check if the account number already exists
        if (isAccountNumberExists(account.getAccountNumber())) {
            throw new DuplicationException(account.getAccountNumber());
        }

        // Update the next account number for automatic generation
        nextAccountNumber = Math.max(nextAccountNumber, account.getAccountNumber() + 1);

        // Reset to 1000 if the maximum range is exceeded
        if (nextAccountNumber > 9999) {
            nextAccountNumber = 1000;
        }

        // Resize the array if needed and add the account
        if (accountCount == accounts.length) {
            resizeAccountsArray();
        }
        accounts[accountCount++] = account;
    }


    public Account getAccountByNumber(int accountNumber) {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccountNumber() == accountNumber) {
                return accounts[i];
            }
        }
        return null;
    }

    public Account[] getAccounts() {
        return accounts;
    }

    private void resizeAccountsArray() {
        Account[] newAccounts = new Account[accounts.length + INITIAL_SIZE];
        for (int i = 0; i < accounts.length; i++) {
            newAccounts[i] = accounts[i];
        }
        accounts = newAccounts;
    }

    public static double calculateMonthlyPayment(double mortgageAmount, int years) {
        int totalMonths = years * 12; // Calculate total number of months
        return mortgageAmount / totalMonths; // Calculate monthly payment
    }

    // Method to generate an automatic account number
    public int generateAccountNumber() {
        // Ensure the generated account number does not already exist
        while (isAccountNumberExists(nextAccountNumber)) {
            nextAccountNumber++;
            if (nextAccountNumber > 9999) {
                nextAccountNumber = 1000; // Reset to 1000 if the range is exceeded
            }
        }
        return nextAccountNumber;
    }

    // Finds the highest existing account number in the system
    private int findHighestAccountNumber() {
        int highestAccountNumber = 999; // Start below the 4-digit range
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccountNumber() > highestAccountNumber) {
                highestAccountNumber = accounts[i].getAccountNumber();
                System.out.println("Highest account number: " + findHighestAccountNumber());

            }
        }
        return highestAccountNumber;
    }

    // Method to check if an account number already exists
    public boolean isAccountNumberExists(int accountNumber) {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccountNumber() == accountNumber) {
                return true;
            }
        }
        return false;
    }

    public String getFormattedBankNumbers() {
        StringBuilder formattedBanks = new StringBuilder("Available bank numbers:\n");
        for (BankNumber bank : BankNumber.values()) {
            formattedBanks.append(bank.getBankNumber())
                    .append(" for ")
                    .append(bank.name())
                    .append("\n");
        }
        return formattedBanks.toString();
    }


    public String displayAllAccounts() throws NoAccountsException {
        if (accountCount == 0) {
            throw new NoAccountsException();
        }

        // Create a sorted copy of the accounts array
        Account[] sortedAccounts = new Account[accountCount];
        System.arraycopy(accounts, 0, sortedAccounts, 0, accountCount);
        Arrays.sort(sortedAccounts); // Sort accounts using the compareTo method in Account

        // Build the output string
        StringBuilder details = new StringBuilder("All Accounts:\n");
        for (Account account : sortedAccounts) {
            details.append(account.displayAccountDetails());
            details.append("-".repeat(50)).append("\n");
        }
        return details.toString();
    }

    public String displayAccountsByType(String accountType) throws NoAccountsException {
        StringBuilder accountsByTypeDetails = new StringBuilder();

        for (int i = 0; i < accountCount; i++) {
            if ((accountType.equals("Regular Checking") && accounts[i] instanceof RegularCheckingAccount) ||
                    (accountType.equals("Business Checking") && accounts[i] instanceof BusinessCheckingAccount) ||
                    (accountType.equals("Savings") && accounts[i] instanceof SavingsAccount) ||
                    (accountType.equals("Mortgage") && accounts[i] instanceof MortgageAccount)) {

                accountsByTypeDetails.append(accounts[i].displayAccountDetails()).append("\n");
            }
        }

        if (accountsByTypeDetails.isEmpty()) {
            throw new NoAccountsException();
        }

        return accountsByTypeDetails.toString();
    }

    public String printManagementFees(Account[] accounts) throws NoAccountsException {
        // Check if accounts exist
        if (accountCount == 0) {
            throw new NoAccountsException();
        }

        StringBuilder report = new StringBuilder();

        for (Account account : accounts) {
            // Check if the account supports charging management fees
            if (account instanceof FeeChargeable feeAccount) {
                double fee = feeAccount.getManagementFee();

                if (fee > 0) { // If there are relevant management fees
                    report.append("Account Number: ")
                            .append(account.getAccountNumber())
                            .append(", Account Type: ")
                            .append(account.getAccountType())
                            .append(", Management Fee: ")
                            .append(Utils.formatCurrency(fee))
                            .append("\n");

                    ceoBonus += fee; // Add to total fees
                }
            }
        }

        report.append("\nTotal Management Fees: ").append(Utils.formatCurrency(ceoBonus));
        return report.toString();
    }


    public void checkIfBusinessAccount(int accountNumber) throws NotBusinessAccountException {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccountNumber() == accountNumber) {
                if (accounts[i] instanceof BusinessCheckingAccount) {
                    return; // Account is a business account
                }
                throw new NotBusinessAccountException(accountNumber); // Account exists but is not business
            }
        }
        throw new IllegalArgumentException("Account number " + accountNumber + " does not exist.");
    }



    public String getAllBusinessAccounts() {
        StringBuilder businessAccounts = new StringBuilder("Business accounts currently available:\n");
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i] instanceof BusinessCheckingAccount) {
                businessAccounts.append("Account Number: ").append(accounts[i].getAccountNumber()).append("\n");
            }
        }
        if (businessAccounts.isEmpty()) {
            businessAccounts.append("No business accounts available.");
        }
        return businessAccounts.toString();
    }


    public String getAccountTypeMenu() {
        StringBuilder menu = new StringBuilder();
        menu.append("Select account type to display total annual profit:\n")
                .append("1. Regular Checking Account\n")
                .append("2. Business Checking Account\n")
                .append("3. Savings Account\n")
                .append("4. Mortgage Account\n")
                .append("Enter your choice: \n");
        return menu.toString();
    }


    public String getAccountTypeName(int choice) {
        StringBuilder accountTypeName = new StringBuilder();

        switch (choice) {
            case 1 -> accountTypeName.append("Regular Checking");
            case 2 -> accountTypeName.append("Business Checking");
            case 3 -> accountTypeName.append("Savings");
            case 4 -> accountTypeName.append("Mortgage");
            default -> throw new IllegalArgumentException("Invalid account type selection.");
        }

        return accountTypeName.toString();
    }


    public String displayProfitableAccounts() throws NoAccountsException {
        // Get profitable accounts
        Account[] profitableAccounts = getProfitableAccounts();

        if (profitableAccounts.length == 0) {
            throw new NoAccountsException();
        }

        // Sort profitable accounts using Array.sort and Comparable
        Arrays.sort(profitableAccounts, new ProfitComparator());


        // Build the output string
        StringBuilder profitableAccountsDetails = new StringBuilder("Profitable Accounts:\n");
        for (Account account : profitableAccounts) {
            profitableAccountsDetails.append("Account Type: ").append(account.getAccountType()).append("\n")
                    .append("Account Number: ").append(account.getAccountNumber()).append("\n")
                    .append("Manager Name: ").append(account.getManagerName()).append("\n")
                    .append("Balance: ").append(Utils.formatCurrency(account.getBalance())).append("\n")
                    .append("Annual Profit: ").append(Utils.formatCurrency(account.calculateProfit())).append("\n")
                    .append("Clients:\n");

            // Add client details
            Client[] clients = account.getClients();
            for (Client client : clients) {
                profitableAccountsDetails.append(" - ").append(client.getName())
                        .append(", Rank: ").append(client.getRank()).append("\n");
            }

            profitableAccountsDetails.append("-".repeat(50)).append("\n");
        }

        return profitableAccountsDetails.toString();
    }

    public String calculateTotalAnnualProfit() throws NoAccountsException {
        if (accountCount == 0) {
            throw new NoAccountsException();
        }
        double totalProfit = 0;
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].isProfitRelevant()) {
                totalProfit += accounts[i].calculateProfit();
            }
        }
        return "Total annual profit for the bank: " + Utils.formatCurrency(totalProfit);
    }


    public String displayMostProfitableCheckingAccount() throws NoAccountsException {
        Account mostProfitable = null;
        double maxProfit = Double.MIN_VALUE;

        // Find the most profitable checking account
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i] instanceof CheckingAccount) {
                double profit = accounts[i].calculateProfit();
                if (profit > maxProfit) {
                    maxProfit = profit;
                    mostProfitable = accounts[i];
                }
            }
        }

        if (mostProfitable == null) {
            throw new NoAccountsException();
        }

        // Create a summary output with just the account type and annual profit
        StringBuilder result = new StringBuilder();
        result.append("Most profitable checking account: ").append(mostProfitable.getClass().getSimpleName()).append("\n");
        result.append("Annual Profit: ").append(Utils.formatCurrency(maxProfit)).append("\n");

        return result.toString();
    }


    private Account[] getProfitableAccounts() {
        int count = 0;
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].isProfitRelevant()) { // Check if the account is profitable
                count++;
            }
        }

        Account[] profitableAccounts = new Account[count];
        int index = 0;
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].isProfitRelevant()) {
                profitableAccounts[index++] = accounts[i];
            }
        }
        return profitableAccounts;
    }


    public String displayTotalAnnualProfitByAccountType(String accountType) throws NoAccountsException {
        double totalProfit = 0;
        boolean hasAccounts = false;

        for (int i = 0; i < accountCount; i++) {
            if ((accountType.equals("Regular Checking") && accounts[i] instanceof RegularCheckingAccount) ||
                    (accountType.equals("Business Checking") && accounts[i] instanceof BusinessCheckingAccount) ||
                    (accountType.equals("Savings") && accounts[i] instanceof SavingsAccount) ||
                    (accountType.equals("Mortgage") && accounts[i] instanceof MortgageAccount)) {

                totalProfit += accounts[i].calculateProfit();
                hasAccounts = true;
            }
        }

        if (!hasAccounts) {
            throw new NoAccountsException();
        }

        return "Total Annual Profit for " + accountType + " Accounts: " + Utils.formatCurrency(totalProfit);
    }


    public double checkProfitVIP(BusinessCheckingAccount originalAccount) {
        // Clone the original account
        BusinessCheckingAccount clonedAccount = originalAccount.clone();

        // Adjust the cloned account's clients to simulate non-VIP status
        for (int i = 0; i < clonedAccount.clientCount; i++) {
            clonedAccount.clients[i] = new Client(clonedAccount.clients[i].getName(), 0); // Reset rank to 0
        }

        // Calculate and return profit for the non-VIP account
        return clonedAccount.calculateProfit();
    }

    public boolean isValidRank(int rank) {
        // Check if the rank is within the valid range (0–10)
        return rank >= 0 && rank <= 10;
    }

    public void checkIfAccountsExist() throws NoAccountsException {
        if (accountCount == 0) { // Using `accountCount` for direct check
            throw new NoAccountsException();
        }
    }

    public void preloadSampleAccounts() throws DuplicationException {
        Account[] sampleAccounts = AccountsFactory.createSampleAccounts(nextAccountNumber);
        for (Account account : sampleAccounts) {
            addAccount(account); // Automatically updates nextAccountNumber
        }
    }




}
