import java.io.*;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

public class Main {

    public static String RESOURCES = "src/main/resources/";
    public static String CSVFILE = "sample.edges.csv";

    public static void main(String[] args) {
        List<Node> allNodes = allNodes(CSVFILE);

        //Question 1
        System.out.println("10 people with most connections sorted by number of connections");
        List<Node> nodes10 = nodesWithMostConnections(allNodes, 10);
        printNodes(nodes10);
        System.out.println();

        //Question 2
        System.out.println("Connections for the 10 people sorted by connection length");
        sortNodesByConnectionLength(nodes10);
        printNodes(nodes10);
        System.out.println();

        //Question 3
        System.out.println("Network sizes for all the people");
        ArrayList<Integer> networkSizes = networkSizes(allNodes);
        System.out.println(networkSizes.toString());
    }

    public static List<Node> nodesWithMostConnections(List<Node> allNodes, int size){
        List<Node> nodesWithMostConns = new ArrayList<>();
        allNodes.sort(Node.connectionsSizeComparator);
        for(int i = 0; i < allNodes.size() && i < size; i++){
            nodesWithMostConns.add(allNodes.get(i));
        }
        return nodesWithMostConns;
    }

    public static void printNodes(List<Node> nodes){
        for(int i = 0; i < nodes.size(); i++){
            System.out.println(nodes.get(i));
        }
    }

    public static ArrayList<Integer> networkSizes(List<Node> nodes){
        ArrayList<Integer> sizes = new ArrayList<>();
        for(int i = 0; i < nodes.size(); i++){
            sizes.add(nodes.get(i).countConnections()+1);
        }
        return sizes;
    }

    public static void sortNodesByConnectionLength(List<Node> nodes){
        for (Node node : nodes) {
            node.sortConnections(Connection.connectionLengthComparator);
        }
    }

    public static List<Node> allNodes(String csvFile) {
        File file = new File(RESOURCES+csvFile);
        BufferedReader bufferedReader = null;
        List<Node> nodes = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();
            while((line = bufferedReader.readLine()) != null){
                String[] data = line.split(",");
                Node node = new Node(data[0].replaceAll("\"", "").trim());
                Node connNode = new Node(data[2].replaceAll("\"", "").trim());
                String relationType = data[1].replaceAll("\"", "").trim();
                String startDate = data[5].replaceAll("\"", "").trim();
                String endDate = data[6].replaceAll("\"", "").trim();
                Connection connection = new Connection(connNode, relationType, startDate, endDate);
                int nodeIndex = nodes.indexOf(node);
                if(nodeIndex != -1){
                    node = nodes.get(nodeIndex);
                    int connectionIndex = node.connectionIndex(connection);
                    if(connectionIndex < 0){
                        node.addConnection(connection);
                    }else{
                        Connection cn = node.getConnectionAt(connectionIndex);
                        cn.setStartDateIfEarlier(connection.getStartDate());
                        cn.setEndDateIfLater(connection.getEndDate());
                    }
                }else{
                    node.addConnection(connection);
                    nodes.add(node);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return nodes;
    }
}
