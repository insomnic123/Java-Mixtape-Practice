import java.util.*;
import java.io.*;

public class Main {

    // Colours from https://www.mymiller.name/wordpress/java/ansi-colors/
    public static final String RESET = "\u001B[0m";
    public static final String BRIGHT_RED = "\u001B[31;1m";
    public static final String BRIGHT_BLUE = "\u001B[34;1m";
    public static final String BRIGHT_MAGENTA = "\u001B[35;1m";
    public static final String BRIGHT_WHITE = "\u001B[37;1m";

    // Goodbye messages generated by ChatGPT
    public static String[] goodbye_messages = {
            "Goodbye… I’m sure you’ve got *just* the right tracks in there…",
            "See you next time. Maybe the mixtape will be better by then?",
            "Well, that’s done. Hope it’s *listenable* at least.",
            "Thanks for… whatever this was. Mixtape saved, I guess.",
            "Alright, it's saved. No promises on it being a hit, though.",
            "Farewell. Maybe consider adding something with taste next time?",
            "Goodbye! I’m sure this is exactly what the world needed.",
            "Until next time. Hopefully you’ll bring better tunes then.",
            "Signing off. Remember, there’s still time to delete a few tracks.",
            "Alright, it’s over. Go on and play it, if you dare."
    };

    // Declaring variables
    static List<Songs> songs = new ArrayList<>();
    static int totalTime = 0;
    static int totalNumSongs = 0;
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    // Print statement because Python > Java
    public static void print(String msg) {
        System.out.println(msg);
    }

    // Start menu method
    public static int startMenu() {
        print(BRIGHT_WHITE + "Please select one of the following!" + RESET);
        print(BRIGHT_BLUE + "[1] View All Songs" + RESET);
        print(BRIGHT_MAGENTA + "[2] Find a Song" + RESET);
        print(BRIGHT_BLUE + "[3] Total Duration" + RESET);
        print(BRIGHT_BLUE + "[4] Total Number of Songs" + RESET);
        print(BRIGHT_MAGENTA + "[5] Make a Genre-based Playlist" + RESET);
        print(BRIGHT_MAGENTA + "[6] Make a Time-based Playlist" + RESET);
        print(BRIGHT_BLUE + "[7] Compare Two Songs" + RESET);
        print(BRIGHT_RED + "[8] Quit" + RESET);

        boolean validInput = false;
        int input;

        // While loop ensuring the user inputs the correct input
        while (!validInput) {
            try {
                input = scanner.nextInt();
                validInput = true;
                return input;
            } catch (InputMismatchException e) {
                print("Invalid input!");
                scanner.nextLine();
            }
        }
        return (1);
    }

    // Method to 'process' the file data
    public static void processData(String filePath) {
        try {
            File myFile = new File(filePath);
            Scanner scnr = new Scanner(myFile);

            // Checks if file exists and ends the program if it doesn't
            if (myFile.exists()) {
                print("File name: " + myFile.getName());
                print("File size in bytes: " + myFile.length());
                // if the first line is a header print it out:
                print("Header: " + scnr.nextLine());
                print("---------------------------------------");
            } else {
                print(BRIGHT_RED + "The file does not exist.");
                System.exit(43110); //  Get it cuz 43110 looks like hello :D
            }
            if (!scnr.hasNext()) {
                print(BRIGHT_RED + "Error: File is empty");
                System.exit(43110);
            }

            while (scnr.hasNextLine()) {
                String line = scnr.nextLine(); // Reads the file
                String[] values = line.split(","); // Splits the data based on Commas, since the file is a CSV (comma separated values)
                String name = values[0].trim(); // Trims the spacing
                String songName = values[1].trim();
                String artist = values[2].trim();
                int year = Integer.parseInt(values[3].trim());
                String genre = values[4].trim();
                String link = values[5].trim();
                int duration = Integer.parseInt(values[6].trim());
                totalTime += duration; // Calculates the total duration
                Songs song = new Songs(name, songName, artist, year, genre, duration, link); // Adds to song method
                songs.add(song); // adds to Song list
                totalNumSongs++; // Counts the number of songs

            }
        } catch (FileNotFoundException e) {
            print(BRIGHT_RED + "File not found: " + e.getMessage()); // Catches errors
        } catch (NumberFormatException e) {
            print(BRIGHT_RED + "Error parsing year: " + e.getMessage());
        }
    }

    // Method to find songs
    public static void findSong() {
        print(BRIGHT_MAGENTA + "[1] Find by User");
        print("[2] Find by Song Name");
        print("[3] Find by Artist");
        print("[4] Find by Release Year");
        print("[5] Find by Duration");
        int choice = scanner.nextInt();
        // Declaring variables
        List<Songs> chosenSongs = new ArrayList<>();
        Set<String> uniqueSongNames = new HashSet<>(); // Using HashSet to find if a song is repeated and to only display one instance of the song
        List<Songs> nonDuplicateSongs = new ArrayList<>();

        switch (choice) {
            case 1:
                print("Please enter the person's name: ");
                scanner.nextLine();
                String name = scanner.nextLine();

                name = name.toLowerCase();
                name = name.substring(0, 1).toUpperCase() + name.substring(1); // Converts first letter of the person's name to upercase

                for (Songs song : songs) {
                    if (name.equalsIgnoreCase(song.getName())) {
                        chosenSongs.add(song);
                    }
                }

                for (Songs song : chosenSongs) {
                    // get the song name in lowercase, trimmed form to handle case insensitivity and extra spaces
                    String songName = song.getSong().trim().toLowerCase();

                    // add to nonDuplicateSongs only if the name is unique
                    if (uniqueSongNames.add(songName)) {
                        nonDuplicateSongs.add(song);
                    }
                }

                if (chosenSongs.isEmpty()) {
                    print(name + " did not submit any songs."); // Displays message if the individual did not submit any songs
                }

                chosenSongs.clear();
                chosenSongs.addAll(nonDuplicateSongs); // Adds all non duplicate songs to a new list

                if (chosenSongs.size() == 1) {
                    print(name + " submitted " + chosenSongs.getFirst().getSong() + " by " + chosenSongs.getFirst().getArtist() + ". Take a listen at: " + chosenSongs.getFirst().link());
                }
                if (chosenSongs.size() > 1) {
                    print(name + " submitted " + chosenSongs.size() + " songs! They submitted: ");

                    for (Songs song : chosenSongs) {
                        print(song.getSong() + " by " + song.getArtist() + ". Take a listen at: " + song.link());
                    }
                }
                return;

            case 2:
                print("Please enter the song name: ");
                scanner.nextLine();
                String songName = scanner.nextLine();

                for (Songs song : songs) {
                    if (song.getSong().equalsIgnoreCase(songName)) {
                        chosenSongs.add(song);
                    }
                }

                for (Songs song : chosenSongs) {
                    String artistName = song.getArtist().trim().toLowerCase();

                    // Add to nonDuplicateSongs only if the name is unique
                    if (uniqueSongNames.add(artistName)) {
                        nonDuplicateSongs.add(song);
                    }
                }

                if (!chosenSongs.isEmpty()) {
                    print("Were you looking for " + chosenSongs.getFirst().getSong() + " by " + chosenSongs.getFirst().getArtist() + "? (Y/N)"); // Confirms if the user found the song they were looking for
                    String response = scanner.nextLine();

                    if (response.equalsIgnoreCase("Y")) {
                        print("Here's the link! " + chosenSongs.getFirst().link());
                    } else {
                        print("I don't have the song you're looking for, sorry!");
                    }
                } else {
                    print("Song not found, Sorry!");
                }
                return;
            case 3:
                print("Please enter the artist's name: ");
                scanner.nextLine();
                String artistName = scanner.nextLine();

                for (Songs song : songs) {
                    if (song.getArtist().equalsIgnoreCase(artistName)) {
                        chosenSongs.add(song);
                    }
                }

                if (chosenSongs.size() == 1) {
                    print("On the mixtape, " + artistName + " has made the song " + chosenSongs.getFirst().getSong()
                            + ". Play it here at: " + chosenSongs.getFirst().link());
                }
                if (chosenSongs.size() > 1) {
                    print(artistName + " has several songs on the playlist! These are: ");

                    for (Songs song : chosenSongs) {
                        print(song.getSong() + " - Play it at: " + song.link());
                    }
                }
                if (chosenSongs.isEmpty()) {
                    print("Sorry, there appears to be no songs by " + artistName + " on the playlist.");
                }
                return;
            case 4:
                print("Please enter the release year: ");
                scanner.nextLine();
                int year = scanner.nextInt();

                for (Songs song : songs) {
                    if (song.getYear() == year) {
                        chosenSongs.add(song);
                    }
                }

                if (chosenSongs.size() > 1) {
                    print("The songs on the mixtape which were released in " + year + " on the playlist are: ");
                    for (Songs song : chosenSongs) {
                        print(song.getSong() + " by " + song.getArtist() + " - Take a listen at: " + song.link());
                    }
                }
                if (chosenSongs.size() == 1) {
                    print("The only song released on " + year + " in the playlist is " + chosenSongs.getFirst().getSong() + " by " + chosenSongs.getFirst().getArtist() + ". Take a listen at: " + chosenSongs.getFirst().link());
                }
                if (chosenSongs.isEmpty()) {
                    print("No songs were released on " + year + " on the playlist.");
                }
                return;
            case 5:
                print("Please enter the number of minutes for any song: ");
                scanner.nextLine();
                int givenDuration = scanner.nextInt();

                for (Songs song : songs) {
                    if ((song.getDuration() / 60) == givenDuration) { // Checks duration based on minutes, ignoring seconds
                        chosenSongs.add(song);
                    }
                }

                if (chosenSongs.size() == 1) {
                    print("The only song that is " + givenDuration + " minutes long is " + chosenSongs.getFirst().getSong() + " by " +
                            chosenSongs.getFirst().getArtist() + ", which comes in at " + (chosenSongs.getFirst().getDuration() / 60) + " minutes and " +
                            (chosenSongs.getFirst().getDuration() % 60) + " seconds. Take a listen at: " + chosenSongs.getFirst().link());
                }
                if (chosenSongs.size() > 1) {
                    print("Multiple songs are " + givenDuration + " minutes long! These are: ");

                    for (Songs song : chosenSongs) {
                        print(song.getSong() + " by " + song.getArtist() + "(" + (song.getDuration() / 60) + " minutes and " +
                                (song.getDuration() % 60) + " seconds) | " + song.link());
                    }
                }
                if (chosenSongs.isEmpty()) {
                    print("Sorry! No songs on the mixtape are " + givenDuration + " minutes long.");
                }
                return;

            default:
                while (choice < 1 || choice > 5) {
                    print("Invalid input, please put in a number between 1 and 5."); // Handles  invalid integer inputs
                    choice = scanner.nextInt();
                }
        }

    }

    // Returns the total time of the playlist
    public static void getTime() {
        var minutes = totalTime / 60; // Wow var !! -- calculates minutes
        var seconds = totalTime % 60; // Calculates total seconds
        System.out.printf(BRIGHT_BLUE + "The total length of the playlist is: %d minutes and %d seconds", minutes, seconds);
    }

    // Displays all Songs
    public static void viewAll() {
        for (Songs song : songs) {
            print(RESET + song);
        }
    }

    // Calculates the total number of songs
    public static void totalSongs() {
        print(BRIGHT_BLUE + "Total Number of Songs: " + totalNumSongs);
    }

    // Creates a playlist based on Genre and returns an arraylist of songs
    public static List<Songs> genrePlaylist() {
        List<String> genres = new ArrayList<>();
        List<Songs> songGenres = new ArrayList<>();

        print(BRIGHT_MAGENTA + "Which genres? Please input your numbers by pressing enter, then write 'end' when you're done! No duplicates please :)");
        print("[1] Classical");
        print("[2] R and B");
        print("[3] Rap");
        print("[4] Pop");
        print("[5] Folk");
        print("[6] Rock");
        print("[7] Country");

        while (true) { // Takes inputs until the user inputs 'end'
            String input = scanner.nextLine();
            genres.add(input);
            if (Objects.equals(input, "end")) {
                genres.removeLast();
                break;
            }
        }

        print(String.valueOf(genres));

        // 'Dictionary' for the genre types
        for (int i = 0; i < genres.size(); i++) {
            if (genres.get(i).equalsIgnoreCase("1")) {
                genres.add("Classical");
            }
            if (genres.get(i).equalsIgnoreCase("2")) {
                genres.add("R and B");
            }
            if (genres.get(i).equalsIgnoreCase("3")) {
                genres.add("Rap");
            }
            if (genres.get(i).equalsIgnoreCase("4")) {
                genres.add("Pop");
            }
            if (genres.get(i).equalsIgnoreCase("5")) {
                genres.add("Folk");
            }
            if (genres.get(i).equalsIgnoreCase("6")) {
                genres.add("Rock");
            }
            if (genres.get(i).equalsIgnoreCase("7")) {
                genres.add("Country");
            }
        }

        print(String.valueOf(genres)); // Adds genre types to the arraylist

        genres.subList(0, (genres.size() / 2)).clear(); // Removes the numbers from the genres arraylist

        print(String.valueOf(genres));

        for (String genre : genres) {
            for (Songs song : songs) {
                if (song.getGenre().equalsIgnoreCase(genre)) {
                    songGenres.add(song);
                }
            }
        }
        return songGenres;
    }

    // Method that creates a playlist based on given duration
    public static void durationPlaylist() {
        List<Songs> toilet; // ...it's best not to question my variable naming
        List<Songs> sideOne = new ArrayList<>(); // For one side of the mixtape
        List<Songs> sideTwo = new ArrayList<>(); // FOr the second side of the mixtape
        int index = 0;
        int sideOneSum = 0; // Sum of time
        int sideTwoSum = 0;

        print(BRIGHT_MAGENTA + "Please select one of the following!");
        print("[1] Based on 'Classic Mixtape' Lengths");
        print("[2] Custom Length");

        scanner.nextLine();

        int input = scanner.nextInt();

        switch (input) {
            case 1:
                print("The 'average' mixtape is ~60 minutes (30 minutes per side). However, given the total length of the mixtape is 79 minutes, we'll make each side 15 minutes.");
                print("Do you have a preference for the genres? (Y/N)"); // Confirms if the user wants to give custom genres
                scanner.nextLine();
                String skibidi = scanner.nextLine();
                if (skibidi.equalsIgnoreCase("Y")) {
                    toilet = genrePlaylist();
                } else {
                    toilet = songs;
                }

                try { // Keeps adding songs to one side until the given time restriction has been reached, in minutes
                    for (int i = 0; sideOneSum / 60 < 15; i++) {
                        sideOne.add(toilet.get(i));
                        sideOneSum += toilet.get(i).getDuration();
                        index = i;
                    }

                    for (; sideTwoSum / 60 < 15; index++) {
                        sideTwo.add(toilet.get(index));
                        sideTwoSum += toilet.get(index).getDuration();
                    }
                } catch (IndexOutOfBoundsException e) {
                    print("Not enough songs! Creating a mixtape from what's possible...");
                }

                print("Side One: ");
                for (Songs song : sideOne) {
                    print(song.getSong() + " by " + song.getArtist() + " - " + song.link() + " (" + song.getGenre() + ")");
                }
                print("Total Time: " + sideOneSum / 60 + " minutes and " + sideOneSum % 60 + " seconds.");


                print("Side Two: ");
                for (Songs song : sideTwo) {
                    print(song.getSong() + " by " + song.getArtist() + " - " + song.link() + " (" + song.getGenre() + ")");
                }
                print("Total Time: " + sideTwoSum / 60 + " minutes and " + sideTwoSum % 60 + " seconds.");

                break;
            case 2:
                print("How long do you want your mixtape to be in minutes?"); // Same thing as before but with custom defined intervals
                scanner.nextLine();
                int time = scanner.nextInt();

                while (time > 70 || time < 7) { // Restricts the user inputs to be between 7 and 70 minutes
                    print("Invalid! Please try again.");
                    time = scanner.nextInt();
                }

                print("Do you have a preference for the genres? (Y/N)");
                scanner.nextLine();
                String temp = scanner.nextLine();
                if (temp.equalsIgnoreCase("Y")) {
                    toilet = genrePlaylist();
                } else {
                    toilet = songs;
                }

                int sideOneTime = time / 2;
                int sideTwoTime = time / 2;

                try {
                    for (int i = 0; sideOneSum / 60 < sideOneTime; i++) {
                        sideOne.add(toilet.get(i));
                        sideOneSum += toilet.get(i).getDuration();
                        index = i;
                    }

                    for (; sideTwoSum / 60 < sideTwoTime; index++) {
                        sideTwo.add(toilet.get(index));
                        sideTwoSum += toilet.get(index).getDuration();
                    }
                } catch (IndexOutOfBoundsException e) {
                    print("Not enough songs! Creating a mixtape from what's possible...");
                }

                print("Side One: ");
                for (Songs song : sideOne) {
                    print(song.getSong() + " by " + song.getArtist() + " - " + song.link() + " (" + song.getGenre() + ")");
                }
                print("Total Time: " + sideOneSum / 60 + " minutes and " + sideOneSum % 60 + " seconds.");


                print("Side Two: ");
                for (Songs song : sideTwo) {
                    print(song.getSong() + " by " + song.getArtist() + " - " + song.link() + " (" + song.getGenre() + ")");
                }
                print("Total Time: " + sideTwoSum / 60 + " minutes and " + sideTwoSum % 60 + " seconds.");

            default:
                while (input > 2 || input < 1) {
                    print("Invalid input! Please try again.");
                    input = scanner.nextInt();
                }
        }
    }

    // Compares songs in Main
    public static void compareSongsM() {
        print(BRIGHT_MAGENTA + "Please input two songs, separated by hitting enter.");
        scanner.nextLine();
        List<Songs> theSongs = new ArrayList<>(); // Arraylist to hold the two songs

        String songNameA = scanner.nextLine();
        String songNameB = scanner.nextLine();

        for (Songs song : songs) {
            if (song.getSong().equalsIgnoreCase(songNameA)) {
                theSongs.add(song);
            }
            if (song.getSong().equalsIgnoreCase(songNameB)) {
                theSongs.add(song);
            } // Adds the two songs
        }
        try {
            if (theSongs.isEmpty()) {
                print("Sorry! No songs were found with those titles"); // Edge Cases
            }
            if (theSongs.size() == 1) {
                print("Sorry! One of the songs doesn't appear to be on the playlist.");
            }
            theSongs.getFirst().compareSongs(theSongs.get(1)); // Uses the compare method in Songs to compare the two songs
            System.exit(43110);
        } catch (IndexOutOfBoundsException e) {
            System.exit(43110);
        }

    }

    public static void main(String[] args) {
        processData("C:\\Users\\qazia\\Desktop\\CS12\\Java Mixtape Practice\\src\\songs.csv"); // Replace with your file directory!

        boolean validInput = false; // Boolean to confirm whether a valid input is given or not
        int choice = startMenu();
        while (!validInput) {
            try {
                switch (choice) {
                    case 1:
                        validInput = true;
                        viewAll();
                        break;
                    case 2:
                        validInput = true;
                        findSong();
                        break;
                    case 3:
                        validInput = true;
                        getTime();
                        break;
                    case 4:
                        validInput = true;
                        totalSongs();
                        break;
                    case 5:
                        validInput = true;
                        List<Songs> values = genrePlaylist();

                        print("Here's your playlist with your chosen genres!: ");

                        for (Songs song : values) {
                            print(song.getSong() + " by " + song.getArtist() + " (" + song.getGenre() + ")" + " - Take a listen at " + song.link());
                        }
                        break;
                    case 6:
                        validInput = true;
                        durationPlaylist();
                        break;
                    case 7:
                        compareSongsM();
                        break;
                    case 8: // Selects random message to display when quitting
                        int max = goodbye_messages.length;
                        int index = random.nextInt(max - 1);
                        print(BRIGHT_RED + goodbye_messages[index] + RESET);
                        System.exit(0);
                        break;

                    default:
                        while (choice < 1 || choice > 8) {
                            print("Invalid input, please try again!");
                            choice = scanner.nextInt();
                        }
                }
            } catch (InputMismatchException e) {
                print("Invalid Input!");
            }
        }
    }
}