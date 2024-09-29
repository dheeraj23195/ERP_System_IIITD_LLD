# IIITD ERP System

This README provides instructions on how to run the IIITD ERP System, outlines the assumptions made during development, and explains how Object-Oriented Programming (OOP) concepts, including generic programming, object classes, and exception handling, are applied in the project.

## How to Run the Code

1. Ensure you have Java Development Kit (JDK) installed on your system.
2. Compile all the Java files in the erp package.
3. Run the Main class.
4. Follow the on-screen prompts to interact with the ERP system.

## New Features and Concepts

### 1. Generic Programming

Generic programming has been incorporated into the IIITD ERP System to enhance flexibility and type safety. Here are some examples:

- Feedback System: The Feedback class is now a generic class, allowing for different types of feedback (text, numeric, etc.). This allows for more versatile feedback collection and storage.

- Generic Collections: The system extensively uses generic collections, such as ArrayList<Courses> in the CourseManager class and HashMap<Integer, Student> in the Login class.

### 2. Object Classes

The system makes extensive use of object classes to represent different entities and concepts:

- User Class Hierarchy: The User class serves as a base class for Student, Professor, and Admin classes, demonstrating inheritance and polymorphism.

- Course-related Classes: Courses, CourseManager, and CompletedCourse (inner class of CourseManager) are examples of object classes representing different aspects of course management.

- Complaint System: The Complaints class and ComplaintManager class work together to handle the complaint system.

- Teaching Assistant (TA) Classes: The system includes TA-related classes that extend the functionality for students who can serve as teaching assistants:
  - TeachingAss class: This class extends the Student class and represents a student who is also a teaching assistant. It includes additional attributes and methods specific to TA duties.
  - TAApplication class: An inner class of Student that represents a student's application to become a TA for a specific course.
  - TAInterface class: Manages the interface and operations specific to TAs, such as viewing assigned courses and managing grades.

These TA-related classes demonstrate further use of inheritance, encapsulation, and specialized object classes within the system.

### 3. Exception Handling

Exception handling has been implemented to make the system more robust and user-friendly:

- Custom Exceptions: The system includes custom exception classes such as InvalidLoginException, CourseFullException, and DropDeadlinePassedException.

- Login Process: The login method in the Login class throws InvalidLoginException for incorrect credentials.

- Course Registration: The addCourse method in CourseManager throws CourseFullException when a course has reached its enrollment limit.

- Course Dropping: The dropCourse method throws DropDeadlinePassedException if a student tries to drop a course after the deadline.

These exceptions are caught and handled in the user interface classes to provide appropriate feedback to the user.

### 4. Object Class

While not explicitly mentioned in the code, the Object class plays a crucial role in the system:

- Implicit Inheritance: All classes in the system implicitly inherit from the Object class, which is the root of Java's class hierarchy.

- Object Methods: Classes in the system inherit and can override methods from the Object class, such as toString(), equals(), and hashCode().

- Usage in the System: Object class methods are used throughout the system, especially when working with collections, comparing objects, or generating string representations of objects.

## Previously Implemented OOP Concepts

The system continues to demonstrate the use of fundamental OOP concepts such as classes and objects, encapsulation, inheritance, polymorphism, interfaces, method overriding, static methods and variables, final keyword, abstract classes, packages, and constructor overloading.

## Conclusion

The IIITD ERP System demonstrates a comprehensive application of object-oriented programming principles, including the new additions of generic programming and exception handling, as well as the fundamental concepts from the Object class. These concepts work together to create a flexible, type-safe, and robust system that can handle various scenarios in academic administration, including the management of teaching assistants.
