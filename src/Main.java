import java.util.*;
import java.io.*;

public class Main {

    static List<Songs> songs = new ArrayList<>();
    static int totalTime = 0;
    static int totalNumSongs = 0;
    static Scanner scanner = new Scanner(System.in);

    public static void print(String msg) {
        System.out.println(msg);
    }

    public static int startMenu() {
        print("[1] View All Songs");
        print("[2] Find a Song");
        print("[3] Total Duration");
        print("[4] Total Number of Songs");
        print("[5] Make a Genre-based Playlist");
        print("[6] Make a Time-based Playlist");
        print("[7] Quit");

        return scanner.nextInt();
    }

    public static void processData(String filePath) {
        try {
            File myFile = new File(filePath);
            Scanner scnr = new Scanner(myFile);


            if (myFile.exists()) {
                print("File name: " + myFile.getName());
                print("File size in bytes " + myFile.length());
                // if the first line is a header print it out:
                print("Header: " + scnr.nextLine());
            } else {
                print("The file does not exist.");
            }

            if (!scnr.hasNext()) {
                print("Error: File is empty");
            }

            while (scnr.hasNextLine()) {

                String line = scnr.nextLine();
                String[] values = line.split(",");
                String name = values[0].trim();
                String songName = values[1].trim();
                String artist = values[2].trim();
                int year = Integer.parseInt(values[3].trim());
                String genre = values[4].trim();
                String link = values[5].trim();
                int duration = Integer.parseInt(values[6].trim());
                totalTime += duration;
                Songs song = new Songs(name, songName, artist, year, genre, duration, link);
                songs.add(song);
                totalNumSongs++;

            }
        } catch (FileNotFoundException e) {
            print("File not found: " + e.getMessage());
        } catch (NumberFormatException e) {
            print("Error parsing year: " + e.getMessage());
        }
    }

    public static void findSong() {
        print("[1] Find by User");
        print("[2] Find by Song Name");
        print("[3] Find by Artist");
        print("[4] Find by Release Year");
        print("[5] Find by Duration");
        int choice = scanner.nextInt();
        List<Songs> chosenSongs = new ArrayList<>();
        Set<String> uniqueSongNames = new HashSet<>();
        List<Songs> nonDuplicateSongs = new ArrayList<>();

        switch (choice) {
            case 1:
                print("Please enter the person's name: ");
                scanner.nextLine();
                String name = scanner.nextLine();

                name = name.toLowerCase();
                name = name.substring(0, 1).toUpperCase() + name.substring(1);

                for (Songs song : songs) {
                    if (name.equalsIgnoreCase(song.getName())) {
                        chosenSongs.add(song);
                    }
                }

                for (Songs song : chosenSongs) {
                    // Get the song name in lowercase, trimmed form to handle case insensitivity and extra spaces
                    String songName = song.getSong().trim().toLowerCase();

                    // Add to nonDuplicateSongs only if the name is unique
                    if (uniqueSongNames.add(songName)) {
                        nonDuplicateSongs.add(song);
                    }
                }

                if (chosenSongs.isEmpty()) {
                    print(name + " did not submit any songs.");
                }

                chosenSongs.clear();
                chosenSongs.addAll(nonDuplicateSongs);

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
                    print("Were you looking for " + chosenSongs.getFirst().getSong() + " by " + chosenSongs.getFirst().getArtist() + "? (Y/N)");
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
                    if ((song.getDuration() / 60) == givenDuration) {
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
                    print("Invalid input, please put in a number between 1 and 5.");
                    choice = scanner.nextInt();
                }
        }

    }

    public static void getTime() {
        var minutes = totalTime / 60;
        var seconds = totalTime % 60;
        System.out.printf("The total length of the playlist is: %d minutes and %d seconds", minutes, seconds);
    }

    public static void viewAll() {
        for (Songs song : songs) {
            print(String.valueOf(song));
        }
    }

    public static void totalSongs() {
        print("Total Number of Songs: " + totalNumSongs);
    }

    public static List<Songs> genrePlaylist() {
        List<String> genres = new ArrayList<>();
        List<Songs> songGenres = new ArrayList<>();

        print("Which genres? Please input your numbers by pressing enter, then write 'end' when you're done!");
        print("[1] Classical");
        print("[2] R and B");
        print("[3] Rap");
        print("[4] Pop");
        print("[5] Folk");
        print("[6] Rock");
        print("[7] Country");

        while (true) {
            String input = scanner.nextLine();
            genres.add(input);
            if (Objects.equals(input, "end")) {
                genres.removeLast();
                break;
            }
        }

        print(String.valueOf(genres));

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

        print(String.valueOf(genres));

        genres.subList(0, (genres.size() / 2)).clear();

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

    public static void durationPlaylist() {
        List<Songs> toilet;
        List<Songs> sideOne = new ArrayList<>();
        List<Songs> sideTwo = new ArrayList<>();
        int index = 0;
        int sideOneSum = 0;
        int sideTwoSum = 0;

        print("Please select one of the following!");
        print("[1] Based on 'Classic Mixtape' Lengths");
        print("[2] Custom Length");

        scanner.nextLine();

        int input = scanner.nextInt();

        switch (input) {
            case 1:
                print("The 'average' mixtape is ~60 minutes (30 minutes per side). However, given the total length of the mixtape is 79 minutes, we'll make each side 15 minutes.");
                print("Do you have a preference for the genres? (Y/N)");
                scanner.nextLine();
                String skibidi = scanner.nextLine();
                if (skibidi.equalsIgnoreCase("Y")) {
                    toilet = genrePlaylist();
                } else {
                    toilet = songs;
                }

                for (int i = 0; sideOneSum / 60 < 15; i++) {
                    sideOne.add(toilet.get(i));
                    sideOneSum += toilet.get(i).getDuration();
                    index = i;
                }

                for (; sideTwoSum / 60 < 15; index++) {
                    sideTwo.add(toilet.get(index));
                    sideTwoSum += toilet.get(index).getDuration();
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

            case 2:
                print("How long do you want your mixtape to be in minutes?");
                scanner.nextLine();
                int time = scanner.nextInt();

                while (time > 60 || time < 7) {
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

                for (int i = 0; sideOneSum / 60 < sideOneTime; i++) {
                    sideOne.add(toilet.get(i));
                    sideOneSum += toilet.get(i).getDuration();
                    index = i;
                }

                for (; sideTwoSum / 60 < sideTwoTime; index++) {
                    sideTwo.add(toilet.get(index));
                    sideTwoSum += toilet.get(index).getDuration();
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

    public static void main(String[] args) {
        processData("C:\\Users\\qazia\\Desktop\\CS12\\Java Mixtape Practice\\src\\songs.csv");

        int choice = startMenu();

        switch (choice) {
            case 1:
                viewAll();
                break;
            case 2:
                findSong();
                break;
            case 3:
                getTime();
                break;
            case 4:
                totalSongs();
                break;
            case 5:
                List<Songs> values = genrePlaylist();

                print("Here's your playlist with your chosen genres!: ");

                for (Songs song : values) {
                    print(song.getSong() + " by " + song.getArtist() + " (" + song.getGenre() + ")" + " - Take a listen at " + song.link());
                }
                break;
            case 6:
                durationPlaylist();
                break;
            case 7:
                System.exit(0);

            default:
                while (choice < 1 || choice > 7) {
                    print("Invalid input, please try again!");
                    choice = scanner.nextInt();
                }
        }
    }
}