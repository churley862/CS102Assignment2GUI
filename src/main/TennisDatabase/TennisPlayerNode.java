package TennisDatabase;

public class TennisPlayerNode  {
    private TennisPlayer player;
    private TennisPlayerNode left,right;
    private TennisMatchesList list;

    public TennisPlayerNode(TennisPlayer player) {
        this.player = player;
        list = new TennisMatchesList();

        left = null;
        right = null;
    }

    public TennisPlayer getPlayer() {
        return player;
    }

    public TennisPlayerNode getLeft() {
        return left;
    }

    public TennisPlayerNode getRight() {
        return right;
    }

    public void printMatches(){
        list.printMatches();
    }

    public void insertMatch(TennisMatch m){
        list.insertMatch(m);
        if (m.getWinnerId().equals(player.getId())) {
            player.addWin();
        } else {
            player.addLoss();
        }
    }

    public void removeMatch(TennisMatch m) {
        if (list.removeMatch(m)) {
            if (m.getWinnerId().equals(player.getId())) {
                player.removeWin();
            } else {
                player.removeLoss();
            }
        }
    }

    public void setLeft(TennisPlayerNode n) { left = n;
    }

    public void setRight(TennisPlayerNode n) { right = n;
    }

    public void setPlayer(TennisPlayer player) {
        this.player = player;
    }
    public String toString(){
        return player.toString();
    }

}
