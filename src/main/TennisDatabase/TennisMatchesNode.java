package TennisDatabase;

public class TennisMatchesNode {
    public TennisMatch match;
    public TennisMatchesNode next,prev;
    // constructor
    public TennisMatchesNode(TennisMatch match){
        this.match = match;
        next = null;
        prev = null;
    }
    //Getters and setters
    public TennisMatch getMatch() {
        return match;
    }

    public TennisMatchesNode getNext() {
        return next;
    }

    public void setNext(TennisMatchesNode n) {
        next = n;
    }

    public TennisMatchesNode getPrev() {
        return prev;
    }

    public void setPrev(TennisMatchesNode p) {
        prev = p;
    }

}
