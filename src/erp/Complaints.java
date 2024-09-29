package erp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Complaints {
    private final String compId;
    private final String compName;
    private final String compDesc;
    private final String compDate;
    private ComplaintStatus compStatus;
    private final int userid;

    public Complaints(int userid, String compName, String compDesc) {
        this.compId = generateComplaintID();
        this.compName = compName;
        this.compDesc = compDesc;
        this.compDate = LocalDate.now().toString();
        this.compStatus = ComplaintStatus.PENDING;
        this.userid = userid;
    }

    private String generateComplaintID() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime now = LocalDateTime.now();
        return "CMP" + dtf.format(now);
    }

    public String getCompId() {
        return compId;
    }

    public String getCompName() {
        return compName;
    }

    public String getCompDesc() {
        return compDesc;
    }

    public String getCompDate() {
        return compDate;
    }

    public ComplaintStatus getCompStatus() {
        return compStatus;
    }

    public int getUserid() {
        return userid;
    }

    public void setCompStatus(ComplaintStatus compStatus) {
        this.compStatus = compStatus;
    }

    public enum ComplaintStatus {
        PENDING, RESOLVED
    }
}