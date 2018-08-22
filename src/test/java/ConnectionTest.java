import org.junit.Test;

import java.net.CookieHandler;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class ConnectionTest {

    private Node connNode = new Node("67890");
    private String startDateStr = "22-SEP-2009";
    private String earlierStartDateStr = "10-OCT-2008";
    private LocalDate startDate = LocalDate.parse(startDateStr, Connection.dateTimeFormatterA);
    private LocalDate earlierStartDate = LocalDate.parse(earlierStartDateStr, Connection.dateTimeFormatterA);
    private String endDateStr = "13-JUL-2013";
    private String laterEndDateStr = "26-NOV-2014";
    private LocalDate endDate = LocalDate.parse(endDateStr, Connection.dateTimeFormatterA);
    private LocalDate laterEndDate = LocalDate.parse(laterEndDateStr, Connection.dateTimeFormatterA);
    private Connection connection = new Connection(connNode,"friend of", startDateStr, endDateStr);

    @Test
    public void getConnectedNode() {
        assertEquals(connection.getConnectedNode(), connNode);
    }

    @Test
    public void getStartDate() {
        assertEquals(connection.getStartDate().format(Connection.dateTimeFormatterA), startDate.format(Connection.dateTimeFormatterA));
    }

    @Test
    public void getEndDate() {
        assertEquals(connection.getEndDate().format(Connection.dateTimeFormatterA), endDate.format(Connection.dateTimeFormatterA));
    }

    @Test
    public void setStartDateIfEarlier() {
        connection.setStartDateIfEarlier(earlierStartDate);
        assertEquals(connection.getStartDate().format(Connection.dateTimeFormatterA), earlierStartDate.format(Connection.dateTimeFormatterA));
        connection.setStartDateIfEarlier(startDate);
        assertEquals(connection.getStartDate().format(Connection.dateTimeFormatterA), earlierStartDate.format(Connection.dateTimeFormatterA));
    }

    @Test
    public void setEndDateIfLater() {
        connection.setEndDateIfLater(laterEndDate);
        assertEquals(connection.getEndDate().format(Connection.dateTimeFormatterA), laterEndDate.format(Connection.dateTimeFormatterA));
        connection.setEndDateIfLater(endDate);
        assertEquals(connection.getEndDate().format(Connection.dateTimeFormatterA), laterEndDate.format(Connection.dateTimeFormatterA));
    }

    @Test
    public void dateFromString() {
        assertEquals(Connection.dateFromString(startDateStr), startDate);
    }

    @Test
    public void equals(){
        boolean result = connection.equals(new Connection(connNode, "friend of", earlierStartDateStr, laterEndDateStr));
        assertTrue(result);
        Node connNode2 = new Node("112233");
        result = connection.equals(new Connection(connNode2, "friend of", earlierStartDateStr, laterEndDateStr));
        assertFalse(result);
        result = connection.equals(new Connection(connNode, "officer of", earlierStartDateStr, laterEndDateStr));
        assertFalse(result);
    }
}