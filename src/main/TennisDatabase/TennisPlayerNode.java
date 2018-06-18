package TennisDatabase;

public class TennisPlayerNode  {
    private TennisPlayer player;
    private TennisPlayerNode left,right;
    private TennisMatchesList list;
    // Constructor
    public TennisPlayerNode(TennisPlayer player) {
        this.player = player;
        list = new TennisMatchesList();

        left = null;
        right = null;
    }
    // Getters
    public TennisPlayer getPlayer() {
        return player;
    }

    public TennisPlayerNode getLeft() {
        return left;
    }

    public TennisPlayerNode getRight() {
        return right;
    }

    // Prints the matches in the list
    public void printMatches(){
        list.printMatches();
    }
    // inserts the match inputted into the list
    public void insertMatch(TennisMatch m){
        list.insertMatch(m);
        if (m.getWinnerId().equals(player.getId())) {
            player.addWin();
        } else {
            player.addLoss();
        }
    }
    // Removes the match inputted
    public void removeMatch(TennisMatch m) {
        if (list.removeMatch(m)) {
            if (m.getWinnerId().equals(player.getId())) {
                player.removeWin();
            } else {
                player.removeLoss();
            }
        }
    }
    // Setters
    public void setLeft(TennisPlayerNode n) { left = n;
    }

    public void setRight(TennisPlayerNode n) { right = n;
    }

    public void setPlayer(TennisPlayer player) {
        this.player = player;
    }
    // The to String function just returns the player toString function
    public String toString(){
        return player.toString();
    }

}
