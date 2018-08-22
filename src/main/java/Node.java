import java.util.ArrayList;
import java.util.Comparator;

public class Node {
    private String nodeId;
    private final ArrayList<Connection> connections;

    public Node(String nodeId){
        this.nodeId = nodeId;
        this.connections = new ArrayList<>();
    }

    public void addConnection(Connection connection){
        this.connections.add(connection);
    }

    public int countConnections(){
        return this.connections.size();
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public int connectionIndex(Connection connection){
        return this.connections.indexOf(connection);
    }

    public Connection getConnectionAt(int index){
        if(index < 0 || index > this.connections.size()-1){
            return null;
        }
        return this.connections.get(index);
    }

    public void sortConnections(Comparator<Connection> comparator){
        this.connections.sort(comparator);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return this.getNodeId().equals(((Node) obj).getNodeId());
    }

    @Override
    public int hashCode() {
        return this.getNodeId().hashCode();
    }

    public String toString(){
        StringBuilder output = new StringBuilder(this.getNodeId());
        for(Connection connection : this.connections){
            output.append("\n\t").append(connection.toString());
        }
        return output.toString();
    }

    public static final Comparator<Node> connectionsSizeComparator = (o1, o2) -> o2.countConnections() - o1.countConnections();
}
