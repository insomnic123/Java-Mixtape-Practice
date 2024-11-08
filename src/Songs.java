import java.util.Objects;

public class Songs {

    // Declaring variables
    private String name = "";
    private String song = "";
    private String artist = "";
    private String genre = "";
    private int year = 0;
    private int duration = 0;
    private String link = "";
    public static final String RESET = "\u001B[0m";
    public static final String BRIGHT_RED = "\u001B[31;1m";
    public static final String BRIGHT_BLUE = "\u001B[34;1m";

    // Constructor Method
    public Songs(String name, String song, String artist, int year, String genre, int duration, String link) {
        this.name = name;
        this.song = song;
        this.artist = artist;
        this.year = year;
        this.duration = duration;
        this.link = link;
        this.genre = genre;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getSong() {
        return song;
    }

    public String getArtist() {
        return artist;
    }

    public int getYear() {
        return year;
    }

    public int getDuration() {
        return duration;
    }

    public String link() {
        return link;
    }

    public String getGenre() {
        return genre;
    }

    // Method to compare two songs
    public void compareSongs(Songs a) {
        if (Objects.equals(a.getGenre(), this.getGenre())) {
            System.out.println("Both songs are " + a.getGenre());
        } else {
            System.out.println(a.getSong() + " by " + a.getArtist() + " is " + a.getGenre() + ", whereas " + this.getSong() + " by " + this.getArtist() + " is " + this.getGenre());
        }
        if ((a.getDuration() / 60) == (this.getDuration() / 60)) {
            System.out.println("Both songs are " + a.getDuration() / 60 + " minutes long.");
        } else {
            System.out.println("Both songs are different in length.");
        }
        if (a.getYear() == this.getYear()) {
            System.out.println("Both songs were released in the year " + a.getYear());
        } else {
            System.out.println("The two songs were released " + Math.abs(a.getYear() - this.getYear()) + " years apart.");
        }
    }

    // toString method
    @Override
    public String toString() {
        return String.format(BRIGHT_RED + "%s by %s %n" + BRIGHT_BLUE + "Released: " + RESET + "%d %n" + BRIGHT_BLUE + "Genre:" + RESET + " %s %n" + BRIGHT_BLUE +
                        "Submitted By:" + RESET + " %s %n" + BRIGHT_BLUE + "Listen at: %s %n" + RESET + "----------",
                song, artist, year, genre, name, link);
    }
}
