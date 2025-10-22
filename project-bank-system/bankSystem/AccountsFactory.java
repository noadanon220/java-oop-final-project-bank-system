package bankSystem;

import java.util.Random;


public class AccountsFactory {
    private static final Random random = new Random();

    public static Account[] createSampleAccounts(int nextAccountNumber) {
        Account[] sampleAccounts = new Account[16]; // Array to hold 16 accounts.
        int accountIndex = 0;

        // Arrays to track used managers and clients to ensure no duplicates.
        String[] usedManagers = new String[16];
        int usedManagersCount = 0;

        // Track used client names to prevent duplication
        String[] usedClients = new String[32]; // Maximum 32 clients for 16 accounts (2 per account)
        int usedClientsCount = 0;

        // Create 4 sets of accounts, one for each type.
        for (int i = 0; i < 4; i++) {
            // Create Regular Checking Account
            String regularManager = getUniqueManager(Manager.REGULAR.getManagers(), usedManagers, usedManagersCount);
            usedManagers[usedManagersCount++] = regularManager;
            sampleAccounts[accountIndex] = createRegularCheckingAccount(
                    nextAccountNumber++, // Assign the next available account number.
                    BankNumber.HAPOALIM_BANK,
                    regularManager
            );
            accountIndex++;

            // Create Business Checking Account
            String businessManager = getUniqueManager(Manager.BUSINESS.getManagers(), usedManagers, usedManagersCount);
            usedManagers[usedManagersCount++] = businessManager;
            sampleAccounts[accountIndex] = createBusinessCheckingAccount(
                    nextAccountNumber++,
                    BankNumber.LEUMI_BANK,
                    businessManager,
                    i // Pass index to customize business revenue for each account.
            );
            accountIndex++;

            // Create Savings Account
            String savingsManager = getUniqueManager(Manager.SAVINGS.getManagers(), usedManagers, usedManagersCount);
            usedManagers[usedManagersCount++] = savingsManager;
            sampleAccounts[accountIndex] = createSavingsAccount(
                    nextAccountNumber++,
                    BankNumber.DISCOUNT_BANK,
                    savingsManager
            );
            accountIndex++;

            // Create Mortgage Account
            String mortgageManager = getUniqueManager(Manager.MORTGAGE.getManagers(), usedManagers, usedManagersCount);
            usedManagers[usedManagersCount++] = mortgageManager;
            sampleAccounts[accountIndex] = createMortgageAccount(
                    nextAccountNumber++,
                    BankNumber.MIZRAHI_TEFAHOT_BANK,
                    mortgageManager
            );
            accountIndex++;
        }

        // Assign unique clients to each account.
        for (Account account : sampleAccounts) {
            if (account != null) {
                // Add two unique clients to each account.
                String client1 = getUniqueClient(ClientName.values(), usedClients, usedClientsCount);
                usedClients[usedClientsCount++] = client1;
                account.addClient(new Client(client1, getRandomRank()));

                String client2 = getUniqueClient(ClientName.values(), usedClients, usedClientsCount);
                usedClients[usedClientsCount++] = client2;
                account.addClient(new Client(client2, getRandomRank()));
            }
        }

        return sampleAccounts;
    }

    // Helper method to retrieves a unique manager name from the list of available managers.
    private static String getUniqueManager(String[] managers, String[] usedManagers, int usedManagersCount) {
        for (String manager : managers) {
            if (!isNameUsed(manager, usedManagers, usedManagersCount)) {
                return manager; // Return the first unused manager.
            }
        }
        throw new IllegalStateException("No unique managers available.");
    }

    // Helper method to retrieves a unique client name from the list of available client names.
    private static String getUniqueClient(ClientName[] clients, String[] usedClients, int usedClientsCount) {
        for (ClientName client : clients) {
            if (!isNameUsed(client.getName(), usedClients, usedClientsCount)) {
                return client.getName(); // Return the first unused client name.
            }
        }
        throw new IllegalStateException("No unique clients available.");
    }

    // Helper method to check if a name is already used
    private static boolean isNameUsed(String name, String[] usedNames, int usedCount) {
        for (int i = 0; i < usedCount; i++) {
            if (usedNames[i].equals(name)) {
                return true;
            }
        }
        return false;
    }

    // Creates a Regular Checking Account.
    public static RegularCheckingAccount createRegularCheckingAccount(int accountNumber, BankNumber bankNumber, String managerName) {
        return new RegularCheckingAccount(accountNumber, bankNumber, managerName, 5000 + accountNumber * 100);
    }

    // Creates a Business Checking Account with varying business revenue.
    public static BusinessCheckingAccount createBusinessCheckingAccount(int accountNumber, BankNumber bankNumber, String managerName, int index) {
        double businessRevenue = switch (index) {
            case 0 -> 9000000; // Less than 10 million
            case 1 -> 9500000; // Less than 10 million
            case 2 -> 10000000; // Exactly 10 million
            case 3 -> 10500000; // Greater than 10 million
            default -> 300000; // Default logic
        };
        return new BusinessCheckingAccount(accountNumber, bankNumber, managerName, 10000 + accountNumber * 200, businessRevenue);
    }

    // Creates a Savings Account with deposit amount and number of years.
    public static SavingsAccount createSavingsAccount(int accountNumber, BankNumber bankNumber, String managerName) {
        return new SavingsAccount(accountNumber, bankNumber, managerName, 20000 + accountNumber * 300, 5 + accountNumber % 3);
    }

    //Creates a Mortgage Account with mortgage amount and monthly payment.
    public static MortgageAccount createMortgageAccount(int accountNumber, BankNumber bankNumber, String managerName) {
        return new MortgageAccount(accountNumber, bankNumber, managerName, 1000000 + accountNumber * 10000, 5000 + accountNumber * 500, 20 + accountNumber % 5);
    }

    // Generates a random rank between 0 and 10 for a client.
    private static int getRandomRank() {
        return random.nextInt(11); // Random rank between 0 and 10
    }
}
