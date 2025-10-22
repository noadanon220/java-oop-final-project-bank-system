package bankSystem;

public class Utils {

    // Method to format currency consistently
    public static String formatCurrency(double amount) {
        return String.format("$%,.2f", amount); // Formats with two decimal places and commas
    }

}
