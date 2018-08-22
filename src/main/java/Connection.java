import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.DAYS;

public class Connection {

    public static DateTimeFormatter dateTimeFormatterA;
    public static DateTimeFormatter dateTimeFormatterB;

    static {
        DateTimeFormatterBuilder dateTimeFBA = new DateTimeFormatterBuilder();
        dateTimeFBA.parseCaseInsensitive();
        dateTimeFBA.appendPattern("d-MMM-y");
        dateTimeFormatterA = dateTimeFBA.toFormatter();

        DateTimeFormatterBuilder dateTimeFBB = new DateTimeFormatterBuilder();
        dateTimeFBB.parseCaseInsensitive();
        dateTimeFBB.appendPattern("d/M/y");
        dateTimeFormatterB = dateTimeFBB.toFormatter();
    }

    private Node connectedNode;
    private String relationType;
    private LocalDate startDate, endDate;

    public Connection(Node connNode, String relationType){
        this.connectedNode = connNode;
        this.relationType = relationType;
        this.startDate = null;
        this.endDate = null;
    }

    public Node getConnectedNode() {
        return this.connectedNode;
    }

    public LocalDate getStartDate(){
        return this.startDate;
    }

    public LocalDate getEndDate(){
        return this.endDate;
    }

    public void setStartDateIfEarlier(LocalDate date){
        if(date != null){
            if(this.startDate == null) {
                this.startDate = date;
            }else{
                if(date.compareTo(this.startDate) < 0){
                    this.startDate = date;
                }
            }
        }
    }

    public void setEndDateIfLater(LocalDate date){
        if(date != null){
            if(this.endDate == null) {
                this.endDate = date;
            }else{
                if(date.compareTo(this.endDate) > 0){
                    this.endDate = date;
                }
            }
        }
    }

    public Connection(Node connNode, String relationType, String startDateStr, String endDateStr){
        this(connNode, relationType);
        this.startDate = dateFromString(startDateStr);
        this.endDate = dateFromString(endDateStr);
    }

    public static LocalDate dateFromString(String dateString){
        LocalDate date = null;
        if(dateString.isEmpty()){
            return date;
        }
        try {
            date = LocalDate.parse(dateString, dateTimeFormatterA);
        }
        catch (DateTimeParseException e){
            try{
                date = LocalDate.parse(dateString, dateTimeFormatterB);
            }
            catch (DateTimeParseException ex){
            }
        }
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection that = (Connection) o;
        return this.connectedNode.equals(that.connectedNode) && this.relationType.equals(that.relationType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(connectedNode, relationType);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder(this.relationType + " " + this.getConnectedNode().getNodeId());
        if(this.startDate != null){
            output.append(" from ").append(this.startDate.format(dateTimeFormatterB));
        }
        if(this.endDate != null){
            output.append(" till ").append(this.endDate.format(dateTimeFormatterB));
        }
        return output.toString();
    }

    public static final Comparator<Connection> connectionLengthComparator = (o1, o2) -> {
        LocalDate o1StartDate = o1.getStartDate();
        LocalDate o1EndDate = o1.getEndDate();
        LocalDate o2StartDate = o2.getStartDate();
        LocalDate o2EndDate = o2.getEndDate();

        if(o1EndDate == null){
            o1EndDate = LocalDate.now();
        }
        if(o2EndDate == null){
            o2EndDate = LocalDate.now();
        }
        if(o1StartDate != null && o2StartDate != null){
            long o1Days = DAYS.between(o1StartDate, o1EndDate);
            long o2Days = DAYS.between(o2StartDate, o2EndDate);
            return (int)(o2Days - o1Days);
        }
        if(o1StartDate == null && o2StartDate != null){
            return -1;
        }
        if(o1StartDate != null){
            return 1;
        }
        return (o2EndDate.compareTo(o1EndDate));
    };
}
