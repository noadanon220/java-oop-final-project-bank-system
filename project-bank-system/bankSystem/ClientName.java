package bankSystem;


public enum ClientName {
    ADELE("Adele"),
    BEYONCE("Beyonce"),
    JUSTIN_BIEBER("Justin Bieber"),
    EMINEM("Eminem"),
    TAYLOR_SWIFT("Taylor Swift"),
    DRAKE("Drake"),
    RIHANNA("Rihanna"),
    ED_SHEERAN("Ed Sheeran"),
    BRUNO_MARS("Bruno Mars"),
    LADY_GAGA("Lady Gaga"),
    ARIANA_GRANDE("Ariana Grande"),
    POST_MALONE("Post Malone"),
    SHAWN_MENDES("Shawn Mendes"),
    THE_WEEKND("The Weeknd"),
    CAMILA_CABELLO("Camila Cabello"),
    BILLIE_EILISH("Billie Eilish"),
    SELENA_GOMEZ("Selena Gomez"),
    CARDI_B("Cardi B"),
    KANYE_WEST("Kanye West"),
    KATY_PERRY("Katy Perry"),
    MARIAH_CAREY("Mariah Carey"),
    ADELE_EXTRAVAGANT("Adele Extravagant"),
    FRANK_SINATRA("Frank Sinatra"),
    ELVIS_PRESLEY("Elvis Presley"),
    FREDDIE_MERCURY("Freddie Mercury"),
    MICHAEL_JACKSON("Michael Jackson"),
    WHITNEY_HOUSTON("Whitney Houston"),
    CELINE_DION("Celine Dion"),
    PAUL_MCCARTNEY("Paul McCartney"),
    JOHN_LENNON("John Lennon"),
    DAVID_BOWIE("David Bowie"),
    PRINCE("Prince");

    private final String name;

    ClientName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
