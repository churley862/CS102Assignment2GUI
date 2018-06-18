package TennisDatabase;

import java.util.Iterator;
import java.util.Stack;

public class TennisPlayerContainerIterator implements Iterator {
    private final TennisPlayersContainer players;
    private Stack<TennisPlayerNode> nodes = new Stack<TennisPlayerNode>();
    private boolean reversed = false;

    public TennisPlayerContainerIterator(TennisPlayersContainer players) {
        this.players = players;
        addNodes(players.root);
    }

    public TennisPlayerContainerIterator(TennisPlayersContainer players, boolean reversed) {
        this.players = players;
        this.reversed = reversed;
        addNodes(players.root);
    }

    void addNodes(TennisPlayerNode node) {
        if (node == null) return;

        nodes.push(node);
        if (reversed)
            addNodes(node.getRight());
        else
            addNodes(node.getLeft());
    }

    @Override
    public boolean hasNext() {
        return !nodes.empty();
    }

    @Override
    public Object next() {
        TennisPlayerNode result = nodes.pop();

        if (reversed)
            addNodes(result.getLeft());
        else
            addNodes(result.getRight());

        return result;
    }
}
