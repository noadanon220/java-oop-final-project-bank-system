package bankSystem;

import Exceptions.DuplicationException;
import Exceptions.NoAccountsException;
import Exceptions.NotBusinessAccountException;

import java.util.Scanner;

// Nofar Liber 312393127, Noa Danon 207229303
public class Main {
    private static final Scanner scan = new Scanner(System.in);
    private static final Bank bank = new Bank();

    // Nofar Liber 312393127, Noa Danon 207229303
    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            showMenu();
            String choice = scan.nextLine();
            try {
                switch (choice) {
                    case "1" -> preloadAccounts();
                    case "2" -> addAccount();
                    case "3" -> addClientToAccount();
                    case "4" -> printAllAccounts();
                    case "5" -> printProfitableAccounts();
                    case "6" -> displayAccountsByType();
                    case "7" -> displayTotalAnnualProfitForAccountType();
                    case "8" -> calculateTotalAnnualProfit();
                    case "9" -> displayMostProfitableAccount();
                    case "10" -> checkVIPProfit();
                    case "11" -> DisplayManagementFee();
                    case "e", "E" -> {
                        System.out.println("Exiting the program. Goodbye!");
                        exit = true;
                    }
                    default ->
                            System.out.println("Invalid choice. Please select an option from the menu (1-11 or E/e)");
                }
            } catch (NoAccountsException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (RuntimeException e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }


    private static void showMenu() {
        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("                  🏦 Bank Management System      ");
        System.out.println("══════════════════════════════════════════════════════════════");
        System.out.println(" 1  → Preload sample accounts ✔️");
        System.out.println(" 2  → Add new account 🆕");
        System.out.println(" 3  → Add client to an account 👤");
        System.out.println(" 4  → Display all accounts 📄");
        System.out.println(" 5  → Display profitable accounts 💰");
        System.out.println(" 6  → Display accounts by type 🔍");
        System.out.println(" 7  → Display annual profit for an account 📈");
        System.out.println(" 8  → Display total annual profit for the bank 💹");
        System.out.println(" 9  → Display the checking account contributing most to profit 🏆");
        System.out.println(" 10 → Check VIP profit for a business account 💼");
        System.out.println(" 11 → Print management fees 📋");
        System.out.println(" E/e  → Exit ❌");
        System.out.println("══════════════════════════════════════════════════════════════");
        System.out.print("Enter your choice:");
        System.out.println();
    }

    // Menu 1:
    private static void preloadAccounts() {
        try {
            bank.preloadSampleAccounts();
            System.out.println("Sample accounts loaded successfully.");
        } catch (DuplicationException e) {
            System.out.println("Error while preloading accounts: " + e.getMessage());
        }
    }


    // Menu 2: ---------------------------------------------------------------------------------------------
    private static void addAccount() {
        while (true) { // Loop until valid input is provided
            try {
                // Get and display the account type menu from the Bank class
                System.out.print(bank.getAccountTypeMenu());

                // Read user input
                String input = scan.nextLine();
                int choice = getChoice(input);

                // If the input is valid, proceed with account creation
                createAndAddAccount(choice);
                break; // Exit the loop after successful account creation

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // Menu 3: ---------------------------------------------------------------------------------------------
    private static void addClientToAccount() {
        try {
            // Check if accounts exist in the system
            bank.checkIfAccountsExist();

            while (true) {
                try {
                    System.out.print("Enter a 4-digit account number to add a client to: ");
                    String input = scan.nextLine().trim();

                    // Validate input
                    if (!input.matches("\\d{4}")) {
                        throw new IllegalArgumentException("Invalid input. Please enter a valid 4-digit account number.");
                    }

                    int accountNumber = Integer.parseInt(input);

                    // Retrieve the account
                    Account account = bank.getAccountByNumber(accountNumber);
                    if (account == null) {
                        throw new IllegalArgumentException("Account not found. Please enter a valid account number.");
                    }

                    // Loop to get a valid client name
                    while (true) {
                        try {
                            // Get client name first
                            String clientName = getValidClientName();

                            // Check if the client already exists in the account
                            if (account.hasClient(clientName)) {
                                throw new IllegalArgumentException("Client already exists in the account. Please enter a different name.");
                            }

                            // If the client doesn't exist, ask for the rank
                            int clientRank = getValidClientRank();

                            // Create client and add it to the account
                            Client client = new Client(clientName, clientRank);
                            account.addClient(client);

                            System.out.println("Client added successfully.");
                            return; // Exit after successful addition
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage() + "\n");
                }
            }
        } catch (NoAccountsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Menu 4: ---------------------------------------------------------------------------------------------
    private static void printAllAccounts() {
        try {
            // Retrieve all account details from the system
            String allAccountsDetails = bank.displayAllAccounts();
            System.out.println(allAccountsDetails); // Display account details
        } catch (NoAccountsException e) {
            // Handle the case where no accounts are available
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Menu 5: ---------------------------------------------------------------------------------------------
    private static void printProfitableAccounts() throws NoAccountsException {
        String profitableAccountsDetails = bank.displayProfitableAccounts();
        System.out.println(profitableAccountsDetails);
    }

    // Menu 6: ---------------------------------------------------------------------------------------------
    private static void displayAccountsByType() {
        try {
            // Check if there are any accounts in the system
            bank.checkIfAccountsExist();

            while (true) { // Loop until a valid account type with existing accounts is selected
                try {
                    // Display the account type menu
                    System.out.print(bank.getAccountTypeMenu());

                    // Read user input
                    String input = scan.nextLine().trim();
                    int choice = Integer.parseInt(input); // Convert input to a number

                    // Get the account type name
                    String accountType = bank.getAccountTypeName(choice);
                    System.out.println("Selected Account Type: " + accountType);

                    // Retrieve accounts by type
                    String accountsByTypeDetails = bank.displayAccountsByType(accountType);

                    // Display the account details and exit the loop if successful
                    System.out.println(accountsByTypeDetails);
                    break;
                } catch (IllegalArgumentException e) {
                    // Handle invalid input
                    System.out.println("Error: " + e.getMessage());
                } catch (NoAccountsException e) {
                    // Handle case where no accounts of the selected type are available
                    System.out.println("Error: " + e.getMessage());
                    break; // Exit the loop and return to the main menu
                }
            }
        } catch (NoAccountsException e) {
            // Handle case where no accounts exist in the system
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Menu 7: ---------------------------------------------------------------------------------------------
    private static void displayTotalAnnualProfitForAccountType() {
        try {
            // Check if any accounts exist in the system
            bank.checkIfAccountsExist();

            // If accounts exist, proceed to display the account type menu
            String accountType;
            while (true) { // Loop until valid input is provided
                try {
                    // Get and display the account type menu from the Bank class
                    System.out.print(bank.getAccountTypeMenu());

                    // Get user input and convert to an integer
                    String choice = scan.nextLine().trim();
                    int accountTypeChoice = Integer.parseInt(choice);

                    // Use the Bank method to retrieve the account type name
                    accountType = bank.getAccountTypeName(accountTypeChoice);

                    // If no exception was thrown, break the loop
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }

            // Once a valid account type is obtained, proceed to display total annual profit
            String result = bank.displayTotalAnnualProfitByAccountType(accountType);
            System.out.println(result);

        } catch (NoAccountsException e) {
            // Handle the case where no accounts exist
            System.out.println("Error: " + e.getMessage());
        }
    }


    // Menu 8: ---------------------------------------------------------------------------------------------
    private static void calculateTotalAnnualProfit() {
        try {
            // Attempt to calculate the total annual profit using the bank's method
            bank.calculateTotalAnnualProfit();
        } catch (NoAccountsException e) {
            // Handle any unexpected exceptions
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Menu 9: ---------------------------------------------------------------------------------------------
    private static void displayMostProfitableAccount() {
        String mostProfitableDetails;
        try {
            mostProfitableDetails = bank.displayMostProfitableCheckingAccount();
        } catch (NoAccountsException e) {
            System.out.println("Error: " + e.getMessage()); // Print the summary in Main
        }

    }

    // Menu 10: ---------------------------------------------------------------------------------------------
    private static void checkVIPProfit() {
        try {
            // Check if accounts exist in the system
            bank.checkIfAccountsExist();

            while (true) { // Loop until valid input is provided
                try {
                    System.out.print("Enter business account number: ");
                    String input = scan.nextLine().trim();

                    // Validate input as numeric
                    if (!input.matches("\\d+")) {
                        throw new IllegalArgumentException("Invalid input. Please enter numeric digits only.");
                    }

                    int accountNumber = Integer.parseInt(input);

                    // Check if the account is a business account
                    bank.checkIfBusinessAccount(accountNumber);

                    // Calculate potential profit for the valid business account
                    Account account = bank.getAccountByNumber(accountNumber);
                    double vipProfit = bank.checkProfitVIP((BusinessCheckingAccount) account);
                    System.out.println("Potential profit if not VIP: " + Utils.formatCurrency(vipProfit));
                    break; // Exit the loop after successful execution

                } catch (NotBusinessAccountException e) {
                    System.out.println("Error: " + e.getMessage());
                    System.out.println("Available business account numbers:");
                    System.out.println(bank.getAllBusinessAccounts());
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } catch (NoAccountsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    // Menu 11: ---------------------------------------------------------------------------------------------
    private static void DisplayManagementFee() {
        try {
            // Check if accounts exist in the system
            bank.checkIfAccountsExist();

            // Retrieve all accounts and generate the management fees summary
            Account[] allAccounts = bank.getAccounts();
            String managementFeesSummary = bank.printManagementFees(allAccounts);

            // Print the management fees summary
            System.out.println(managementFeesSummary);

        } catch (NoAccountsException e) {
            // Handle the case where no accounts exist
            System.out.println("Error: " + e.getMessage());
        }
    }


    // Supporting Methods
    private static void createAndAddAccount(int choice) {
        try {
            int accountNumber = getAccountNumberFromUser();

            // Select bank using the enum
            BankNumber bankNumber = selectBankCode();
            System.out.println("Selected Bank: " + bankNumber.name());

            // Select manager based on account type
            String managerName = switch (choice) {
                case 1 -> selectManager(Manager.REGULAR);
                case 2 -> selectManager(Manager.BUSINESS);
                case 3 -> selectManager(Manager.SAVINGS);
                case 4 -> selectManager(Manager.MORTGAGE);
                default -> throw new IllegalStateException("Unexpected account type choice.");
            };

            String clientName = getValidClientName();

            Account account = switch (choice) {
                case 1 -> createRegularCheckingAccount(accountNumber, bankNumber, managerName);
                case 2 -> createBusinessCheckingAccount(accountNumber, bankNumber, managerName);
                case 3 -> createSavingsAccount(accountNumber, bankNumber, managerName);
                case 4 -> createMortgageAccount(accountNumber, bankNumber, managerName);
                default -> throw new IllegalStateException("Unexpected account type choice.");
            };

            // Add the new account to the bank
            bank.addAccount(account);

            // Add the client to the account
            Client client = new Client(clientName, 5); // Default rank of 5
            account.addClient(client);

            System.out.println("Account added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid number.");
        } catch (DuplicationException e) {
            System.out.println("Error: Account already exists. " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static int getAccountNumberFromUser() {
        String choice;

        // Validate input for manual account number entry
        while (true) {
            try {
                System.out.print("Do you want to enter account number manually? ('Y/y' for Yes or 'N/n' for No): ");
                choice = scan.nextLine().trim().toUpperCase();

                // Validate input is either "Y" or "N"
                if (!choice.equals("Y") && !choice.equals("N")) {
                    throw new IllegalArgumentException("Invalid input. Please enter 'Y/y' for Yes or 'N/n' for No.");
                }
                break; // Exit loop when input is valid
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        if (choice.equals("Y")) { // Manual input option
            while (true) {
                try {
                    System.out.print("Enter a 4-digit account number: ");
                    int accountNumber = Integer.parseInt(scan.nextLine().trim());

                    // Validate that the number is a 4-digit number
                    if (accountNumber < 1000 || accountNumber > 9999) {
                        throw new IllegalArgumentException("Account number must be a 4-digit number.");
                    }

                    // Check if the account number already exists
                    if (bank.isAccountNumberExists(accountNumber)) {
                        throw new DuplicationException(accountNumber);
                    }

                    // Update the next account number for automatic generation
                    bank.nextAccountNumber = Math.max(bank.nextAccountNumber, accountNumber + 1);

                    return accountNumber;
                } catch (NumberFormatException e) {
                    System.out.println("Error: Please enter a valid 4-digit number.");
                } catch (DuplicationException e) {
                    System.out.println(e.getMessage() + " Please try a different account number.");
                }
            }
        } else { // Automatic generation option
            return bank.generateAccountNumber();
        }
    }

    // Method to get a valid client name
    private static String getValidClientName() {
        while (true) {
            try {
                System.out.print("Enter client's name: \n");
                String clientName = scan.nextLine().trim();

                // Validate that the name contains only letters and spaces
                if (!clientName.matches("[a-zA-Z ]+")) {
                    throw new IllegalArgumentException("Invalid name. Please enter only letters and spaces.\n");
                }

                return clientName; // Return valid name
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage() + "\n");
            }
        }
    }

    // Method to get a valid credit limit
    private static double getValidCreditLimitFromUser() {
        while (true) {
            try {
                System.out.print("Enter credit limit: \n");
                String input = scan.nextLine().trim();

                // Validate that the input is a valid number
                double creditLimit = Double.parseDouble(input);

                // Check that the credit limit is a positive number
                if (creditLimit <= 0) {
                    throw new IllegalArgumentException("Credit limit must be a positive number.\n");
                }

                return creditLimit; // Return valid credit limit
            } catch (NumberFormatException e) {
                // Handle case where input is not a number
                System.out.println("Error: Please enter a valid numeric value.\n");
            } catch (IllegalArgumentException e) {
                // Handle case where input is not a positive number
                System.out.println("Error: " + e.getMessage() + "\n");
            }
        }
    }

    private static int getValidClientRank() {
        while (true) { // Loop until valid input is provided
            try {
                System.out.print("Enter client rank (0-10): ");
                String input = scan.nextLine().trim();

                // Parse input as integer
                int rank = Integer.parseInt(input);

                // Validate rank using Bank's method
                if (!bank.isValidRank(rank)) {
                    throw new IllegalArgumentException("Rank must be between 0 and 10.");
                }

                return rank; // Return valid rank

            } catch (NumberFormatException e) {
                // Handle non-numeric input
                System.out.println("Error: Please enter a numeric value between 0 and 10.\n");
            } catch (IllegalArgumentException e) {
                // Handle out-of-range rank
                System.out.println("Error: " + e.getMessage() + "\n");
            }
        }
    }

    private static Account createRegularCheckingAccount(int accountNumber, BankNumber bankNumber, String managerName) {
        System.out.print("Enter credit limit: ");
        double credit = getValidCreditLimitFromUser();
        return new RegularCheckingAccount(accountNumber, bankNumber, managerName, credit);
    }

    private static Account createBusinessCheckingAccount(int accountNumber, BankNumber bankNumber, String managerName) {
        System.out.print("Enter credit limit: ");
        double credit = Double.parseDouble(scan.nextLine());
        System.out.print("Enter business revenue: ");
        double revenue = Double.parseDouble(scan.nextLine());
        return new BusinessCheckingAccount(accountNumber, bankNumber, managerName, credit, revenue);
    }

    private static Account createSavingsAccount(int accountNumber, BankNumber bankNumber, String managerName) {
        System.out.print("Enter deposit amount: ");
        double depositAmount = Double.parseDouble(scan.nextLine());
        System.out.print("Enter number of years: ");
        int years = Integer.parseInt(scan.nextLine());
        return new SavingsAccount(accountNumber, bankNumber, managerName, depositAmount, years);
    }

    private static Account createMortgageAccount(int accountNumber, BankNumber bankNumber, String managerName) {
        System.out.print("Enter original mortgage amount: ");
        double mortgageAmount = Double.parseDouble(scan.nextLine().trim());

        System.out.print("Enter number of years: ");
        int years = Integer.parseInt(scan.nextLine().trim());

        // Use the Bank class to calculate the monthly payment
        double monthlyPayment = Bank.calculateMonthlyPayment(mortgageAmount, years);

        // Display the calculated monthly payment to the user
        System.out.println("Your calculated monthly payment is: " + Utils.formatCurrency(monthlyPayment));
        return new MortgageAccount(accountNumber, bankNumber, managerName, mortgageAmount, monthlyPayment, years);
    }


    // Utility Methods with user input
    private static BankNumber selectBankCode() {
        while (true) {
            try {
                System.out.print(bank.getFormattedBankNumbers());
                System.out.print("Enter the bank number: \n");
                String input = scan.nextLine().trim();

                int bankNumber = Integer.parseInt(input); // Validate numeric input
                for (BankNumber bank : BankNumber.values()) {
                    if (bank.getBankNumber() == bankNumber) {
                        return bank; // Valid selection
                    }
                }

                // If no match, throw an exception
                throw new IllegalArgumentException("Invalid bank number. Please choose a valid number.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a numeric value.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }


    private static String selectManager(Manager managerType) {
        String[] managers = managerType.getManagers();

        while (true) {
            try {
                // Display the manager menu
                System.out.println("Select a manager:");
                for (int i = 0; i < managers.length; i++) {
                    System.out.println((i + 1) + ". " + managers[i]);
                }

                // Prompt user for input
                System.out.print("Enter your choice: ");
                String input = scan.nextLine().trim();

                // Validate input as numeric
                int choice = Integer.parseInt(input);

                // Ensure choice is within valid range
                if (choice < 1 || choice > managers.length) {
                    throw new IllegalArgumentException("Invalid selection. Please choose a valid manager number.");
                }

                return managers[choice - 1]; // Return selected manager

            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a numeric value.\n");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }


    private static int getChoice(String input) {
        int choice;

        try {
            choice = Integer.parseInt(input); // Attempt to parse input as a number
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input. Please enter a number between 1 and 4.");
        }

        // Validate input is within the range
        if (choice < 1 || choice > 4) {
            throw new IllegalArgumentException("Invalid selection. Please choose a valid account type (1-4).");
        }
        return choice;
    }


}
