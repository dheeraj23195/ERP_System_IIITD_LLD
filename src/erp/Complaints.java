package erp;
import java.time.LocalDate;

public class Complaints {
    private int compId;
    private String compName;
    private String compDesc;
    private String compDate;
    private String compStatus;
    private int userid;

    public Complaints(int userid,String compName, String compDesc, String compStatus ) {
        this.compName = compName;
        this.compDesc = compDesc;
        this.compDate = LocalDate.now().toString();
        this.compStatus = "Pending";
        this.userid = userid;
    }
}
