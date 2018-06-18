package TennisDatabase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;


public class TennisMatchesContainer {
    private ObservableList<TennisMatch> matches = FXCollections.observableArrayList();

    public boolean matchExists(String event) {
        for (TennisMatch match : matches) {
            if (match.getTournament().equals(event))
                return true;
        }
        return false;
    }

    // Need to ask about self contained insert and sort

    public void insertMatch(TennisMatch tennisMatch) {
        int insert_point = 0;
        while (insert_point < matches.size() && matches.get(insert_point).compareTo(tennisMatch) > 0) {
            insert_point++;
        }
        matches.add(insert_point,tennisMatch);
      }

    public void removeMatch(TennisMatch match) {
        matches.remove(match);
    }

    public void printAllMatches() {
        for (TennisMatch match : matches) {
            match.print();
        }
    }

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
    public List<TennisMatch> returnAllPlayerMatches(TennisPlayer player){
        List<TennisMatch> playerMatches = new ArrayList<>();
        for (TennisMatch match : matches) {
            if (match.getPlayer2Id() == player.getId() || match.getPlayer1Id() == player.getId()){
                playerMatches.add(match);
            }
        }
        return playerMatches;
    }

    public List<TennisMatch> returnMatches(){
        return matches;
    }

    public ObservableList<TennisMatch> getMatchList() {
        return matches;
    }

    public void reset() {
        matches.clear();
    }
}
