package main.java.TennisDatabase;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class TennisDatabase{
    private TennisPlayersContainer players = new TennisPlayersContainer();
    private TennisMatchesContainer matches = new TennisMatchesContainer();
    public TennisPlayer searchTennisPlayer(String id){
        return players.getPlayerById(id).getPlayer();
    }
    public void reset(){
        players = new TennisPlayersContainer();
        matches = new TennisMatchesContainer();
    }
    public void loadFromFile(String fName) throws FileNotFoundException {

        Scanner sc = new Scanner(new File(fName));
        while (sc.hasNextLine()) {
            parseLine(sc.nextLine());
        }
    }
// TODO check for bad data lines
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

    public void printAllPlayers() {
        players.printAllPlayers();
    }


    public void printMatchesOfPlayer(String playerId) {
        players.printMatchesOfPlayer(playerId);
    }


    public void printAllMatches() {
        matches.printAllMatches();
    }

    public void insertPlayer(String id, String firstName, String lastName, int year, String country) {
        players.insertPlayer(new TennisPlayer(id, firstName, lastName, year, country));
    }


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
    public void removePlayer(TennisPlayer player){
        players.removeNode(player);
    }
}
