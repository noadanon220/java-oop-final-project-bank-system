package bankSystem;

import java.util.Comparator;

public class ProfitComparator implements Comparator<Account> {
    @Override
    public int compare(Account a, Account b) {
        // Compare accounts by profit in descending order
        return Double.compare(b.calculateProfit(), a.calculateProfit());
    }
}
