package TennisDatabase;
import javafx.collections.ObservableList;

import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class TennisDatabase{
    private TennisPlayersContainer players = new TennisPlayersContainer();
    private TennisMatchesContainer matches = new TennisMatchesContainer();

    public ObservableList<TennisPlayer> getPlayers() { return players.getPlayerList(); }
    public ObservableList<TennisMatch> getMatches() { return matches.getMatchList(); }

    // input: TennisPlayer
    // Desc.: inserts a player into the tennis player container
    public void addPlayer(TennisPlayer player) {
        players.insertPlayer(player);
    }

    // input: TennisMatch
    // Desc.: inserts a match into the tennis matches container
    public void addMatch(TennisMatch match) {
        matches.insertMatch(match);
    }

    // input: String id : id of tennis player to search for
    // output: TennisPlayer found by ID
    // Desc.: Searches for a TennisPlayer given an ID
    public TennisPlayer searchTennisPlayer(String id){
        return players.getPlayerById(id).getPlayer();
    }
    public void reset(){
        players.reset();
        matches.reset();
    }
    // input: String fName : filename of the file to be inputed into the database
    // Desc.: Loads players and matches from a file
    public void loadFromFile(String fName) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(fName));
        while (sc.hasNextLine()) {
            parseLine(sc.nextLine());
        }
    }
// input: String of the player or match to be inputted
// Desc.: looks at the line of data passed in and parses it into players and matches to be added to the database
public void parseLine(String s) {
    Scanner sc = new Scanner(s).useDelimiter("/");
    String lineType = sc.next();
    if (lineType.equalsIgnoreCase("Player")) {
        insertPlayer(sc.next(), sc.next(), sc.next(), sc.nextInt(), sc.next());
    } else if (lineType.equalsIgnoreCase("Match")) {
        String player1 = sc.next();
        String player2 = sc.next();
        int date = sc.nextInt();
        int year = date / 10000;
        int month = (date - (year * 10000)) / 100;
        int day = date % 100;
        String tourn = sc.next();
        String scores = sc.next();
        insertMatch(player1, player2, year, month, day, tourn, scores);
    } else {
        System.out.println("The inputed data" + s + "is not valid as a player or match");
    }

}
    // desc.: Prints all the players to the consol
    public void printAllPlayers() {
        players.printAllPlayers();
    }

    // Output: ArrayList<TennisPlayer> of all the players in the database
    // description: returns an array list of tennis players
    public ArrayList<TennisPlayer> returnAllPlayers() {return players.returnAllTennisPlayers();}

    // Output: ArrayList<TennisMatches> of all the players in the database
    // description: returns an array list of tennis Matches
    public ArrayList<TennisMatch> returnAllMatches() {return (ArrayList<TennisMatch>) matches.returnMatches();};


    // input: String of a player ID
    // description: Prints the matches of this player to the consol
    public void printMatchesOfPlayer(String playerId) {
        players.printMatchesOfPlayer(playerId);
    }


    // description: Prints all the matches in the database
    public void printAllMatches() {
        matches.printAllMatches();
    }

    // input: String (id, firstName,lastName, country) int (year) for the player to be created
    // description: creates a player and adds it to the players container
    public void insertPlayer(String id, String firstName, String lastName, int year, String country) {
        players.insertPlayer(new TennisPlayer(id, firstName, lastName, year, country));
    }

    // input: String (idP1,idP2,Location,score) int (year, month,day) for the match to be created
    // description: creates a match and adds it to the matches container
    public void insertMatch(String idPlayer1, String idPlayer2, int year, int month, int day, String tournament, String score)  {
        TennisPlayerNode player1 = players.getPlayerById(idPlayer1);
        if (player1 == null) {
            TennisPlayer player = new TennisPlayer(idPlayer1);
            players.insertPlayer(player);
        }
        TennisPlayerNode player2 = players.getPlayerById(idPlayer2);
        if (player2 == null) {
            TennisPlayer player = new TennisPlayer(idPlayer2);
            players.insertPlayer(player);
        }

        TennisMatch match = new TennisMatch(players.getPlayerById(idPlayer1).getPlayer(),
                players.getPlayerById(idPlayer2).getPlayer(), year, month, day, tournament, score);
        matches.insertMatch(match);
        players.insertMatch(match);
    }
    // input: TennisMatch - match to be deleted
    // desc: Takes a match and deletes it from the matches container and the players container for all players with this match
    public void removeMatch(TennisMatch match) {
        matches.removeMatch(match);
        players.removeMatch(match);
    }

    // input: String - filename to export to
    // desc.: exports to the filename inputted
    public void exportToFile(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        Scanner matchesScanner = new Scanner(matches.returnAllMatches());
        Scanner playerScanner = new Scanner(players.toString());
        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        while (matchesScanner.hasNextLine()) {
            writer.println(matchesScanner.nextLine());
        }
        while (playerScanner.hasNextLine()){
            writer.println(playerScanner.nextLine());
        }
        writer.close();
    }
    // input: TennisPlayer - the player to be removed
    // Desc: Removes a player from the players container
    public void removePlayer(TennisPlayer player){
        players.removeNode(player);
    }

    // input: TennisPlayer - the player to delete all of their matches from
    // desc: deletes all the matches of the inputed player
    public void deleteMatchesOfPlayer(TennisPlayer player) {
        for (TennisMatch match : matches.returnAllPlayerMatches(player)) {
            removeMatch(match);
        }
    }

    // Input: the ID of the match to check for a duplicate
    // Output: The Boolean If a match exists already
    // Desc: returns true if a match exists for a given player
    public boolean matchExists(String id) {
        return matches.matchExists(id);
    }
    // Input: the ID of the player to check for existance
    // Output: The Boolean If the player exists for that id
    // Desc: returns true if a duplicate player exists for a given id
    public boolean hasPlayer(String id) {
        return players.getPlayerById(id) != null;
    }
}
