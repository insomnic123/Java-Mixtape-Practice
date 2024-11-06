public class Songs {

    private String name = "";
    private String song = "";
    private String artist = "";
    private String genre = "";
    private int year = 0;
    private int duration = 0;
    private String link = "";

    public Songs(String name, String song, String artist, int year, String genre, int duration, String link){
        this.name = name;
        this.song = song;
        this.artist = artist;
        this.year = year;
        this.duration = duration;
        this.link = link;
        this.genre = genre;
    }

    public String getName() {return name;}

    public String getSong() {return song;}

    public String getArtist() {return artist;}

    public int getYear() {return year;}

    public int getDuration() {return duration;}

    public String link() {return link;}

    public String getGenre() {return genre;}

    @Override
    public String toString() {
//        return ("Submitted by: " + name + ", the song is: " + song + " by " + artist + " released in the year " + year + ". It's a " + genre + " song, coming in at " + duration + " seconds long. Take a listen at: " + link );
        return ("Song: " + song + " | Artist" + artist);
    }
}
