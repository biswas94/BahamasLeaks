import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class NodeTest {

    private String nodeId = "12345";
    private Node node = new Node(nodeId);
    private Node connNode1 = new Node("67890");
    private Node connNode2 = new Node("112233");
    private String startDateStr1 = "22-SEP-2009";
    private String endDateStr1 = "13-JUL-2013";
    private String startDateStr2 = "13-JUL-2010";
    private String endDateStr2 = "13-JUL-2012";
    private Connection connection1 = new Connection(connNode1,"friend of", startDateStr1, endDateStr1);
    private Connection connection2 = new Connection(connNode2,"friend of", startDateStr2, endDateStr2);

    @Test
    public void addConnection() {
        assertEquals(0, node.countConnections());
        node.addConnection(connection1);
        assertEquals(1, node.countConnections());
        assertEquals(node.getConnectionAt(0), connection1);
    }

    @Test
    public void countConnections() {
        node.addConnection(connection1);
        assertEquals(1, node.countConnections());
        node.addConnection(connection2);
        assertEquals(2, node.countConnections());
    }

    @Test
    public void getNodeId() {
        assertEquals(nodeId, node.getNodeId());
    }

    @Test
    public void connectionIndex() {
        node.addConnection(connection1);
        node.addConnection(connection2);
        assertEquals(1, node.connectionIndex(connection2));
        assertEquals(0, node.connectionIndex(connection1));
    }

    @Test
    public void getConnectionAt() {
        node.addConnection(connection2);
        node.addConnection(connection1);
        assertEquals(connection2, node.getConnectionAt(0));
        assertEquals(connection1, node.getConnectionAt(1));
    }

    @Test
    public void sortConnections() {
        node.addConnection(connection2);
        node.addConnection(connection1);
        assertEquals(node.getConnectionAt(0), connection2);
        assertEquals(node.getConnectionAt(1), connection1);
        node.sortConnections(Connection.connectionLengthComparator);
        assertEquals(node.getConnectionAt(0), connection1);
        assertEquals(node.getConnectionAt(1), connection2);
    }

    @Test
    public void equals() {
        assertEquals(node, new Node(nodeId));
    }
}