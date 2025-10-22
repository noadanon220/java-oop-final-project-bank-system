package bankSystem;


public class Client {
    private String name;
    private int rank; // Between 0 and 10

    public Client(String name, int rank) {
        this.name = name;
        setRank(rank);
    }


    public String getName() {
        return name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        if (rank < 0 || rank > 10) {
            throw new IllegalArgumentException("Rank must be between 0 and 10.");
        }
        this.rank = rank;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Client) {
            Client other = (Client) obj;
            return this.name.equalsIgnoreCase(other.name);
        }
        return false;
    }

    public static boolean isClientExist(Client[] clients, String name) {
        Client clientToCheck = new Client(name, 0); // Temporary client object to check equality
        for (Client client : clients) {
            if (client != null && client.equals(clientToCheck)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return name + ", Rank: " + rank;
    }

}
