package erp;

import java.time.LocalTime;
import java.util.List;

public class Schedule {
    private static final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private static final LocalTime START_TIME = LocalTime.of(9, 0);
    private static final LocalTime END_TIME = LocalTime.of(18, 30);
    private static final int TIME_SLOT = 30;

    public static void displaySchedule(Schedulable user) {
        if (user instanceof Student) {
            displayStudentSchedule((Student) user);
        } else if (user instanceof Professor) {
            displayProfessorSchedule((Professor) user);
        } else {
            System.out.println("Unknown user type for schedule display.");
        }
    }

    private static void displayStudentSchedule(Student student) {
        List<Courses> registeredCourses = student.courseManager.getRegisteredCourses();
        displayWeeklySchedule(student.getName(), registeredCourses);
    }

    private static void displayProfessorSchedule(Professor professor) {
        List<Courses> taughtCourses = professor.getTaughtCourses().stream()
                .map(Courses::getCourse)
                .filter(course -> course != null)
                .collect(java.util.stream.Collectors.toList());
        displayWeeklySchedule("Professor " + professor.getName(), taughtCourses);
    }

    private static void displayWeeklySchedule(String name, List<Courses> courses) {
        System.out.println("Weekly Schedule for " + name);
        System.out.println("----------------------------------------------------");
        System.out.printf("%-10s", "Time");
        for (String day : DAYS) {
            System.out.printf("%-20s", day);
        }
        System.out.println();
        System.out.println("----------------------------------------------------");
        LocalTime currentTime = START_TIME;
        while (currentTime.isBefore(END_TIME) || currentTime.equals(END_TIME)) {
            System.out.printf("%-10s", currentTime);

            for (String day : DAYS) {
                Courses course = findCourse(courses, day, currentTime);
                if (course != null) {
                    System.out.printf("%-20s", course.code + " - " + course.getAssignedProfessor().getName());
                } else {
                    System.out.printf("%-20s", "");
                }
            }
            System.out.println();
            currentTime = currentTime.plusMinutes(TIME_SLOT);
        }
    }

    private static Courses findCourse(List<Courses> courses, String day, LocalTime time) {
        for (Courses course : courses) {
            for (String courseDay : course.days) {
                if (courseDay.substring(0, 3).equalsIgnoreCase(day.substring(0, 3))) {
                    if ((time.equals(course.starttime) || time.isAfter(course.starttime))
                            && time.isBefore(course.endtime)) {
                        return course;
                    }
                }
            }
        }
        return null;
    }
}