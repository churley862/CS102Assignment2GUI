package TennisDatabase;

public class TennisMatchesList {
    private TennisMatchesNode head = null;

    public void removeMatch(TennisMatch m) {
        TennisMatchesNode node = head;

        while (node.getMatch().compareTo(m) < 0) {
           node = node.getNext();

           // bail out
           if (node == head) break;
        }

        if (node.getMatch().compareTo(m) == 0) {
           node.getPrev().setNext(node.getNext());
           node.getNext().setPrev(node.getPrev());
        }
    }

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
