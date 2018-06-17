package TennisDatabase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;


public class TennisMatchesContainer {
    private ObservableList<TennisMatch> matches = FXCollections.observableArrayList();

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
