package erp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Feedback<T> {
    private T feedback;
    private static Map<String, List<Feedback<?>>> allFeedbacks = new HashMap<>();

    public Feedback(T feedback) {
        this.feedback = feedback;
    }

    public Feedback() {
    }

    public T getFeedback() {
        return feedback;
    }

    public void setFeedback(T feedback) {
        this.feedback = feedback;
    }

    public void displayFeedback(int number) {
        System.out.println(number + ". " + feedback);
    }

    public static void addFeedback(String courseCode, int semester, Feedback<?> feedback) {
        String key = courseCode + "_" + semester;
        allFeedbacks.computeIfAbsent(key, k -> new ArrayList<>()).add(feedback);
    }

    public static List<Feedback<?>> getFeedbackForCourse(String courseCode, int semester) {
        String key = courseCode + "_" + semester;
        return allFeedbacks.getOrDefault(key, new ArrayList<>());
    }

    public static void displayAllFeedbackForCourse(String courseCode, int semester) {
        List<Feedback<?>> feedbacks = getFeedbackForCourse(courseCode, semester);
        System.out.println("Feedback for " + courseCode + " (Semester " + semester + "):");
        if (feedbacks.isEmpty()) {
            System.out.println("No feedback available for this course.");
        }
        else {
            for (int i = 0; i < feedbacks.size(); i++) {
                feedbacks.get(i).displayFeedback(i + 1);
            }
        }
    }
}