package bankSystem;

public enum Manager {
    // Managers for different account types
    REGULAR("Alice", "Bob", "Charlie", "David"),
    BUSINESS("Eve", "Frank", "Grace", "Hank"),
    SAVINGS("Ivy", "Jack", "Kate", "Leo"),
    MORTGAGE("Mona", "Nina", "Oscar", "Paul");

    private final String[] managers;

    Manager(String... managers) {
        this.managers = managers;
    }

    public String[] getManagers() {
        return managers;
    }
}
