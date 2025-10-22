package bankSystem;

import java.util.Date;

public abstract class Account implements Comparable<Account> {
    protected int accountNumber;
    protected Date openDate;
    protected BankNumber bankNumber;
    protected double balance;
    protected final int BONUS = 20;
    protected String managerName;
    protected Client[] clients;
    protected int clientCount;
    protected static final int INITIAL_SIZE = 2;
    protected static final double RATE_DIFFERENCE = 0.10; // Assuming 10% rate difference

    public Account(int accountNumber, BankNumber bankNumber, String managerName) {
        this.accountNumber = accountNumber;
        this.openDate = new Date();
        this.bankNumber = bankNumber;
        this.balance = BONUS; // Bonus for opening an account
        this.managerName = managerName;
        clients = new Client[INITIAL_SIZE];
        clientCount = 0;
    }

    // Abstract methods
    public abstract String getAccountType();

    public abstract double calculateProfit();

    public abstract boolean isProfitRelevant();

    // Getters
    public int getAccountNumber() {
        return accountNumber;
    }

    public Client[] getClients() {
        Client[] clientList = new Client[clientCount];
        for (int i = 0; i < clientCount; i++) {
            clientList[i] = clients[i];
        }
        return clientList;
    }

    // Getter for balance
    public double getBalance() {
        return balance;
    }

    // Getter for manager name
    public String getManagerName() {
        return managerName;
    }

    public void addClient(Client client) throws IllegalArgumentException {
        String newClientName = client.getName().toLowerCase();
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].getName().toLowerCase().equals(newClientName)) {
                throw new IllegalArgumentException("Client already exists in the account.");
            }
        }
        if (clientCount == clients.length) {
            resizeClientsArray();
        }
        clients[clientCount++] = client;
    }

    private void resizeClientsArray() {
        Client[] newClients = new Client[clients.length + INITIAL_SIZE];
        for (int i = 0; i < clients.length; i++) {
            newClients[i] = clients[i];
        }
        clients = newClients;
    }

    public boolean hasClient(String clientName) {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].getName().equalsIgnoreCase(clientName)) {
                return true;
            }
        }
        return false;
    }

    public String displayAccountDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Account Type: ").append(getAccountType()).append("\n");
        details.append("Account Number: ").append(accountNumber).append("\n");
        details.append("Bank Number: ").append(bankNumber.name()).append(" (").append(bankNumber.getBankNumber()).append(")\n");
        details.append("Open Date: ").append(openDate).append("\n");
        details.append("Balance: ").append(Utils.formatCurrency(balance)).append("\n");
        details.append("Manager Name: ").append(managerName).append("\n");
        details.append("Clients:\n");
        for (int i = 0; i < clientCount; i++) {
            details.append(" - ").append(clients[i]).append("\n");
        }
        return details.toString();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); // Default shallow cloning
    }

    @Override
    public int compareTo(Account other) {
        // Sorting accounts by their account number in ascending order
        return Integer.compare(this.accountNumber, other.accountNumber);
    }


}