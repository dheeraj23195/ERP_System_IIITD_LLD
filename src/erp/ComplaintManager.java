package erp;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ComplaintManager {
    private static final Map<String, Complaints> complaintsMap = new HashMap<>();

    public static void addComplaint(Complaints complaint) {
        complaintsMap.put(complaint.getCompId(), complaint);
    }

    public static Complaints.ComplaintStatus getComplaintStatus(String complaintId) {
        Complaints complaint = complaintsMap.get(complaintId);
        if (complaint != null) {
            return complaint.getCompStatus();
        }
        System.out.println("Complaint not found");
        return null;
    }

    public static Complaints getComplaint(String complaintId) {
        return complaintsMap.get(complaintId);
    }

    public static Collection<Complaints> getAllComplaints() {
        return complaintsMap.values();
    }
}