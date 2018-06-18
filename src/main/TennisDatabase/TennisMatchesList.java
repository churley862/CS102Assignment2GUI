package TennisDatabase;

public class TennisMatchesList {
    private TennisMatchesNode head = null;

    // input: TennisMatch
    // Desc: the match that is inputted is removed
    // output: if the removal is a success it returns true else returns false
    public boolean removeMatch(TennisMatch m) {
        TennisMatchesNode node = head;

        while (node.getMatch().compareTo(m) < 0) {
           node = node.getNext();

           // bail out
           if (node == head) break;
        }

        if (node.getMatch().compareTo(m) == 0) {
           node.getPrev().setNext(node.getNext());
           node.getNext().setPrev(node.getPrev());
           return true;
        }
        return false;
    }
    // input TennisMatch to be inserted
    // Desc: inserts the match into the container
    public void insertMatch(TennisMatch m) {
        TennisMatchesNode node = new TennisMatchesNode(m);

        if (head == null) {
            head = node;
            node.setNext(node);
            node.setPrev(node);
        } else {

            TennisMatchesNode insertPoint = head;
            while (insertPoint.getMatch().compareTo(m) < 0) {
                insertPoint = insertPoint.getNext();

                if (insertPoint == head) break;
            }

            node.setNext(insertPoint);
            node.setPrev(insertPoint.getPrev());
            insertPoint.setPrev(node);
            node.getPrev().setNext(node);

            // handle special case inserting at front of list
            if (head == insertPoint && insertPoint.getMatch().compareTo(m) > 0) {
                head = node;
            }
        }
    }
    // Prints all the matches in the list
    public void printMatches(){
        TennisMatchesNode node = head;
        if (node != null) {
            do {
                node.getMatch().print();
                node = node.getNext();
            } while (node != head);
        }else{
            System.out.println("There are no matches for this player");
        }
    }

}
