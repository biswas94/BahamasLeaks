import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void allNodes() {
        assertTrue(Main.allNodes(Main.CSVFILE).size() > 0);
    }

    @Test
    public void nodesWithMostConnections() {
        List<Node> nodesWithMostConns = Main.nodesWithMostConnections(Main.allNodes(Main.CSVFILE), 10);
        assertTrue(nodesWithMostConns.size() <= 10);
        assertTrue(areNodesSortedByNumConns(nodesWithMostConns));
    }

    public boolean areNodesSortedByNumConns(List<Node> nodes){
        if(nodes.size() < 1){
            return true;
        }
        int size = nodes.get(0).countConnections();
        for(int i = 1; i < nodes.size(); i++){
            if(nodes.get(i).countConnections() > size){
                return false;
            }else{
                size = nodes.get(i).countConnections();
            }
        }
        return true;
    }


    @Test
    public void networkSizes() {
        assertTrue(Main.networkSizes(Main.allNodes(Main.CSVFILE)).size() > 0);
    }

    @Test
    public void sortNodesByConnectionLength() {
        List<Node> allNodes = Main.allNodes(Main.CSVFILE);
        List<Node> nodes10 = Main.nodesWithMostConnections(allNodes, 10);
        Main.sortNodesByConnectionLength(nodes10);
    }

}