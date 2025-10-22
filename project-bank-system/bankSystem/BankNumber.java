package bankSystem;

public enum BankNumber {
    HAPOALIM_BANK(12),                  // Bank Hapoalim
    LEUMI_BANK(10),                     // Bank Leumi
    DISCOUNT_BANK(11),                  // Discount Bank
    MIZRAHI_TEFAHOT_BANK(20),           // Mizrahi Tefahot Bank
    OTZAR_HAYAL_BANK(14),               // Bank Otzar Ha-Hayal
    MERCANTILE_DISCOUNT_BANK(17),       // Mercantile Discount Bank
    YAHAV_BANK(4),                      // Bank Yahav for Government Employees
    FIRST_INTERNATIONAL_BANK(31);      // First International Bank of Israel


    private final int bankCode;

    BankNumber(int code) {
        this.bankCode = code;
    }

    public int getBankNumber() {
        return bankCode;
    }
}


