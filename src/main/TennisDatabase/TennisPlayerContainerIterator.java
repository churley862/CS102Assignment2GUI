package TennisDatabase;

import java.util.Iterator;
import java.util.Stack;

public class TennisPlayerContainerIterator implements Iterator {
    private final TennisPlayersContainer players;
    private Stack<TennisPlayerNode> nodes = new Stack<TennisPlayerNode>();
    private boolean reversed = false;

    // Constructor
    public TennisPlayerContainerIterator(TennisPlayersContainer players) {
        this.players = players;
        addNodes(players.root);
    }

    // Constructor for the reversed order
    public TennisPlayerContainerIterator(TennisPlayersContainer players, boolean reversed) {
        this.players = players;
        this.reversed = reversed;
        addNodes(players.root);
    }

    // Adds the nodes inserted to the stack
    void addNodes(TennisPlayerNode node) {
        if (node == null) return;

        nodes.push(node);
        if (reversed)
            addNodes(node.getRight());
        else
            addNodes(node.getLeft());
    }

    @Override
    // Checks if the stack has a next value
    public boolean hasNext() {
        return !nodes.empty();
    }

    @Override
    // gets the stacks next value
    public Object next() {
        TennisPlayerNode result = nodes.pop();

        if (reversed)
            addNodes(result.getLeft());
        else
            addNodes(result.getRight());

        return result;
    }
}
