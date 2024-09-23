package erp;

public interface Gradable {
    void addGrade(String courseCode, String grade);
    void displayGrades(int semester);
    double calculateGPA();
}