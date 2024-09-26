Here is the updated README with your GitHub repository link included:

---

# IIITD ERP System

## Overview

The IIITD ERP System is a console-based application developed in Java to manage various academic operations such as course registration, grading, and complaints. This system caters to students, professors, and administrators, providing role-specific interfaces and functionality.

## Prerequisites

- Java Development Kit (JDK) installed on your system (version 8 or higher).

## How to Run

1. Clone this repository:
   ```bash
   git clone https://github.com/dheeraj23195/ERP_System_IIITD_LLD.git
   ```
2. Navigate to the project directory:
   ```bash
   cd ERP_System_IIITD_LLD
   ```
3. Compile the Java files:
   ```bash
   javac erp/*.java
   ```
4. Run the main class:
   ```bash
   java erp.Main
   ```

Follow the on-screen prompts to interact with the system.

## Assumptions

- This is a console-based application with no graphical user interface (GUI).
- Data is stored in memory and is not persisted between runs.
- The system follows a semester-based academic calendar.
- Professors manually enter student grades.
- A student’s credit limit per semester is 20.
- Course codes are unique identifiers for courses.

## Features

### 1. **System Initialization**
   - Initializes courses, login system, and interfaces for students, professors, and admins.

### 2. **User Authentication**
   - Users can sign up or log in with their credentials (ID, password, and role).
   - Role-based interfaces are provided:
     - **Students**: Manage courses, grades, and complaints.
     - **Professors**: Manage courses, enrolled students, and grades.
     - **Admins**: Manage courses, users, and system-wide operations.

### 3. **Student Operations**
   - View course schedule.
   - Add/drop courses (within credit limits).
   - View grades and calculate GPA.
   - Register complaints and check their status.

### 4. **Professor Operations**
   - View and manage assigned courses.
   - Update course details (syllabus, timings, etc.).
   - Assign grades to students.

### 5. **Admin Operations**
   - Manage courses, students, and professors.
   - Handle complaints and assign professors to courses.

### 6. **Course Management**
   - Central repository for all courses.
   - Manage course attributes (code, name, credits, prerequisites, timings, and assigned professors).

### 7. **Grading System**
   - Professors assign grades to students.
   - Students can view their GPA/CGPA.

### 8. **Complaint System**
   - Students register complaints, which are handled by admins.

### 9. **Logout and Exit**
   - Users can log out or exit the system. The program runs until explicitly exited.

## OOP Concepts Applied

### 1. **Classes and Objects**
   - Classes represent entities such as `Student`, `Professor`, `Course`, and `Complaint`.

### 2. **Encapsulation**
   - Private fields and public methods protect sensitive data (e.g., `rollNo`, `phoneNo` in `Student`).

### 3. **Inheritance**
   - `Student` and `Professor` classes inherit from `User` class, sharing common attributes (`id`, `name`).

### 4. **Polymorphism**
   - Role-specific methods like `displayInfo()` are overridden for each user type while maintaining uniformity through the `User` class.

### 5. **Interfaces**
   - `Gradable` interface ensures methods for adding grades, displaying grades, and calculating GPA are present in all implementing classes.

### 6. **Method Overriding**
   - Methods like `displayInfo()` are overridden to display specific information for different user roles.

### 7. **Static Methods and Variables**
   - Static variables (like the list of courses) are used for system-wide management.

### 8. **Final Keyword**
   - Final constants, like default admin credentials, are immutable after initialization.

### 9. **Abstract Classes**
   - The `User` class may be abstract, providing common structure but leaving certain methods to be implemented by subclasses.

### 10. **Packages**
   - All classes are part of the `erp` package to organize related components.

### 11. **Exception Handling**
   - Error handling is implemented to manage invalid inputs or other potential issues gracefully.

### 12. **Generic Collections**
   - Collections like `ArrayList` are used to manage data (e.g., `ArrayList<Course>` for storing available courses).

### 13. **Constructor Overloading**
   - Classes like `Student` may have overloaded constructors for flexible object creation.

## Contribution Guidelines

If you'd like to contribute to the IIITD ERP System, please fork the repository and submit a pull request. Ensure your code follows best practices and is well-documented.

---

