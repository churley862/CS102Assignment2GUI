package TennisDatabase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

public class TennisPlayersContainer {
    ObservableList<TennisPlayer> playerList;
    TennisPlayerNode root;
    // constructor
    public TennisPlayersContainer() {
        root = null;
        playerList = FXCollections.observableArrayList();
    }
    // Returns the observable list of tennis players
    public ObservableList<TennisPlayer> getPlayerList() {
        return playerList;
    }

    // inserts a player passed into the function into the BST in order
    public void insertPlayer(TennisPlayer player) {
        boolean newPlayer = true;
        for (TennisPlayer p : playerList) {
            if (p.compareTo(player) == 0) {
                newPlayer = false;
            }
        }
        if (newPlayer) playerList.add(player);

        if (root == null) {
            insertFirstNode(player);
        }
        else {
            TennisPlayerNode insert_point = findInsertNode(root, player);
            if(insert_point.getPlayer().compareTo(player) == 0){
                insert_point.getPlayer().updatePlayer(player);
            } else if (insert_point.getPlayer().compareTo(player) < 0) {
                insert_point.setLeft(new TennisPlayerNode(player));
            } else {
                insert_point.setRight(new TennisPlayerNode(player));
            }
        }
    }
    // function to find the correct insert node
    // passed in the current node and the player to insert
    private TennisPlayerNode findInsertNode(TennisPlayerNode node, TennisPlayer player) {
        if(node.getPlayer().compareTo(player) == 0) {
            return node;
        }else if(node.getPlayer().compareTo(player) < 0){
            if (node.getLeft() == null) return node;
            return findInsertNode(node.getLeft(), player);
        }
        else{
            if (node.getRight() == null) return node;
            return findInsertNode(node.getRight(), player);
        }
    }

    // the function used to insert a player if it is the first node
    private void insertFirstNode(TennisPlayer player) {
        if (root != null) {
            throw new RuntimeException("tree already populated, cannot call insertFirstNode");
        }

        TennisPlayerNode node = new TennisPlayerNode(player);
        root = node;
    }

    // removes a match that is passed in recursively this is the first call
    public void removeMatch(TennisMatch match) {
        removeMatch(root, match);
        refresh();
    }
    // continued from above this is the rest of the calls
    void removeMatch(TennisPlayerNode node, TennisMatch match) {
        if (node == null) return;

        removeMatch(node.getLeft(), match);
        node.removeMatch(match);
        removeMatch(node.getRight(), match);
    }
    // checks if both players exist, if they do then the match is added, else the dummy player is created and the match is created
    public void insertMatch(TennisMatch match) {

        TennisPlayerNode node = getPlayerById(match.getPlayer1Id());
        if (node == null) {
            TennisPlayer player = new TennisPlayer(match.getPlayer1Id());
            insertPlayer(player);
            node = getPlayerById(match.getPlayer1Id());
        }
        node.insertMatch(match);

        node = getPlayerById(match.getPlayer2Id());
        if (node == null) {
            TennisPlayer player = new TennisPlayer(match.getPlayer2Id());
            insertPlayer(player);
            node = getPlayerById(match.getPlayer2Id());
        }
        node.insertMatch(match);

        refresh();
    }
    // updates the player list
    public void refresh() {
        // hacky way to update the collection
        playerList.set(0, playerList.get(0));
    }
    // Returns an arraylist of all the tennis players in the BST recursively
    public ArrayList<TennisPlayer> returnAllTennisPlayers(){
        return returnAllTennisPlayers(root);
    }
    // the above function continued
    private ArrayList<TennisPlayer> returnAllTennisPlayers(TennisPlayerNode node) {
        ArrayList<TennisPlayer> tennisPlayerList = new ArrayList<TennisPlayer>();
        if (node == null){return tennisPlayerList;}
        tennisPlayerList = (returnAllTennisPlayers(node.getLeft()));
        tennisPlayerList.add(node.getPlayer());
        tennisPlayerList.addAll(returnAllTennisPlayers(node.getRight()));
        return tennisPlayerList;
    }

    // prints all players in the BST recursively
    public void printAllPlayers(){
        printAllPlayers(root);
    }
    // the above function continued
    private void printAllPlayers(TennisPlayerNode node) {
        if (node == null) {return;}

        printAllPlayers(node.getLeft());
        node.getPlayer().print();
        printAllPlayers(node.getRight());
    }
    // The to string fuction of the players contatiner that recursively prints all in the tree
    public String toString(){
        return toString(root);
    }
    // function continued from above
    private String toString(TennisPlayerNode node) {
        if (node == null) { return ""; }
        String allPlayers = toString(node.getLeft());
        allPlayers += node.getPlayer() + "\n";
        allPlayers += toString(node.getRight());
        return allPlayers;
    }
    // prints all matches of the player corresponding to the inputted ID
    public void printMatchesOfPlayer(String playerId) {
        if(getPlayerById(playerId) != null)
            getPlayerById(playerId).printMatches();
        else {
            System.out.println("This player does not have any matches in the database.");
        }

    }
    // Gets the player node from the inputted ID recursively
    public TennisPlayerNode getPlayerById(String id){
        return getPlayerById(root, id);


    }
    // function continued from above
    TennisPlayerNode getPlayerById(TennisPlayerNode node, String id) {
        if (node == null){return null;}
        int comp = node.getPlayer().getId().compareTo(id);
        if (comp == 0) return node;
        if (comp < 0) return getPlayerById(node.getLeft(), id);
        return getPlayerById(node.getRight(), id);
    }
    // find node recursively takes the current node the parent and the player and finds it in the tree
    private TennisPlayerNode[] findNode(TennisPlayer p, TennisPlayerNode parent, TennisPlayerNode node) {
        if (parent == null || node == null) {
            TennisPlayerNode[] result = new TennisPlayerNode[2];
            return result;
        }
        if (node.getPlayer().equals(p)){
            TennisPlayerNode[] result = new TennisPlayerNode[2];
            result[0] = parent;
            result[1] = node;
            return result;
        }
        if (node.getPlayer().compareTo(p) < 0)
            return findNode(p, node, node.getLeft());
        else
            return findNode(p,node,node.getRight());
    }
    // Find node takes the player and returns the node and the parent and the current node and the other in the array
    private TennisPlayerNode[] findNode(TennisPlayer p) {
        if (root == null) {
            TennisPlayerNode[] result = new TennisPlayerNode[2];
            return result;
        }

        if (root.getPlayer().equals(p)) {
            TennisPlayerNode[] result = new TennisPlayerNode[2];
            result[1] = root;
            return result;
        }

        if (root.getPlayer().compareTo(p) < 0)
            return findNode(p, root, root.getLeft());
        else
            return findNode(p,root,root.getRight());
    }
    // Sets the pointer of the parent node to the next
    private void setParentPointer(TennisPlayerNode parent, TennisPlayerNode old, TennisPlayerNode next) {
        if (parent.getLeft() == old) {
            parent.setLeft(next);
        } else {
            parent.setRight(next);
        }
    }
    // Finds the lowest node and returns it
    private TennisPlayerNode findLowestNode(TennisPlayerNode node){
        if(node.getLeft() == null){
            return node;
        }else{
            return findLowestNode(node.getLeft());
        }
    }
    // Returns a true if the inputted player is found in the tree and removed correctly
    public boolean removeNode(TennisPlayer p) {
        playerList.remove(p);

        TennisPlayerNode[] removePoint = findNode(p);
        TennisPlayerNode parent = removePoint[0];
        TennisPlayerNode node = removePoint[1];

        if (node == null)
            return false;

        if (node.getLeft() == null && node.getRight() == null) {
            if (parent == null) {
                root = null;
            } else {
                setParentPointer(parent, node, null);
            }
            return true;
        }

        if (node.getLeft() == null) {
            if (parent == null) {
                root = node.getRight();
            } else {
                setParentPointer(parent, node, node.getRight());
            }
        } else if (node.getRight() == null) {
            if (parent == null) {
                root = node.getLeft();
            } else {
                setParentPointer(parent, node, node.getLeft());
            }
        } else {
            TennisPlayerNode lowest = findLowestNode(node.getLeft());
            removeNode(lowest.getPlayer());
            node.setPlayer(lowest.getPlayer());
        }
        return true;
    }
    // Resets the player list
    public void reset() {
        playerList.clear();
        root = null;
    }

}
