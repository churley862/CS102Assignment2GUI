package TennisDatabase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;


public class TennisMatchesContainer {
    private ObservableList<TennisMatch> matches = FXCollections.observableArrayList();
    // input: String of the event name
    // output: returns if a match exists already
    public boolean matchExists(String event) {
        for (TennisMatch match : matches) {
            if (match.getTournament().equals(event))
                return true;
        }
        return false;
    }

    // Input: tennis match
    // Inputs the match into the container in order
    public void insertMatch(TennisMatch tennisMatch) {
        int insert_point = 0;
        while (insert_point < matches.size() && matches.get(insert_point).compareTo(tennisMatch) > 0) {
            insert_point++;
        }
        matches.add(insert_point,tennisMatch);
      }
    // input TennisMatch
    // Desc: removes a match passed to the method
    public void removeMatch(TennisMatch match) {
        matches.remove(match);
    }

    // Prints all the matches in the container
    public void printAllMatches() {
        for (TennisMatch match : matches) {
            match.print();
        }
    }

    // Output: string with all the matches is returned
    public String returnAllMatches(){
        String allMatches ="";
        for (TennisMatch match : matches) {
            if (!allMatches.isEmpty()){
                allMatches = allMatches + "\n" + match.toString();
            }else{
                allMatches = match.toString();
            }
        }
        return allMatches;
    }
    // output an arraylist of all tennis matches of a player is returned
    // input the player whos matches to return
    public List<TennisMatch> returnAllPlayerMatches(TennisPlayer player){
        List<TennisMatch> playerMatches = new ArrayList<>();
        for (TennisMatch match : matches) {
            if (match.getPlayer2Id() == player.getId() || match.getPlayer1Id() == player.getId()){
                playerMatches.add(match);
            }
        }
        return playerMatches;
    }

    //Returns all matches of the player
    public List<TennisMatch> returnMatches(){
        return matches;
    }

    // returns an observable list
    public ObservableList<TennisMatch> getMatchList() {
        return matches;
    }

    // Resets the database
    public void reset() {
        matches.clear();
    }
}
