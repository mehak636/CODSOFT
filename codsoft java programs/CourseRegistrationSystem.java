import java.util.*;

// Class to represent a Course
class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private int registeredStudents;
    private String schedule;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.registeredStudents = 0;
        this.schedule = schedule;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getRegisteredStudents() {
        return registeredStudents;
    }

    public String getSchedule() {
        return schedule;
    }

    public boolean hasAvailableSlots() {
        return registeredStudents < capacity;
    }

    public void registerStudent() {
        if (hasAvailableSlots()) {
            registeredStudents++;
        } else {
            throw new IllegalStateException("No available slots for this course.");
        }
    }

    public void removeStudent() {
        if (registeredStudents > 0) {
            registeredStudents--;
        } else {
            throw new IllegalStateException("No students to remove.");
        }
    }

    @Override
    public String toString() {
        return "Course Code: " + courseCode +
               ", Title: " + title +
               ", Description: " + description +
               ", Capacity: " + capacity +
               ", Registered: " + registeredStudents +
               ", Schedule: " + schedule;
    }
}

// Class to represent a Student
class Student {
    private String studentID;
    private String name;
    private List<Course> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(Course course) {
        if (registeredCourses.contains(course)) {
            System.out.println("You are already registered for this course.");
        } else if (course.hasAvailableSlots()) {
            course.registerStudent();
            registeredCourses.add(course);
            System.out.println("Successfully registered for course: " + course.getTitle());
        } else {
            System.out.println("Course is full.");
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.remove(course)) {
            course.removeStudent();
            System.out.println("Successfully dropped course: " + course.getTitle());
        } else {
            System.out.println("You are not registered for this course.");
        }
    }
}

// Main class for the Student Course Registration System
public class CourseRegistrationSystem {
    private List<Course> courses;
    private List<Student> students;

    public CourseRegistrationSystem() {
        courses = new ArrayList<>();
        students = new ArrayList<>();
        loadSampleData();
    }

    private void loadSampleData() {
        // Add sample courses
        courses.add(new Course("CS101", "Introduction to Programming", "Learn basic programming concepts.", 30, "MWF 9:00-10:00 AM"));
        courses.add(new Course("MA201", "Calculus II", "Advanced calculus topics.", 25, "TTh 11:00-12:30 PM"));
        courses.add(new Course("PH301", "Physics III", "Modern physics and applications.", 20, "MWF 2:00-3:00 PM"));

        // Add sample students
        students.add(new Student("S123", "Alice Johnson"));
        students.add(new Student("S124", "Bob Smith"));
    }

    public void displayCourses() {
        System.out.println("\n=== Available Courses ===");
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    public Student findStudent(String studentID) {
        for (Student student : students) {
            if (student.getStudentID().equals(studentID)) {
                return student;
            }
        }
        return null;
    }

    public Course findCourse(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                return course;
            }
        }
        return null;
    }

    public void handleStudentRegistration(Scanner scanner) {
        System.out.print("Enter your student ID: ");
        String studentID = scanner.next();
        Student student = findStudent(studentID);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("\nHello, " + student.getName() + "!");

        int choice;
        do {
            System.out.println("\n=== Student Menu ===");
            System.out.println("1. Register for a Course");
            System.out.println("2. Drop a Course");
            System.out.println("3. View Registered Courses");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayCourses();
                    System.out.print("Enter course code to register: ");
                    String courseCode = scanner.next();
                    Course courseToRegister = findCourse(courseCode);
                    if (courseToRegister != null) {
                        student.registerCourse(courseToRegister);
                    } else {
                        System.out.println("Invalid course code.");
                    }
                    break;
                case 2:
                    System.out.println("\n=== Registered Courses ===");
                    for (Course c : student.getRegisteredCourses()) {
                        System.out.println(c);
                    }
                    System.out.print("Enter course code to drop: ");
                    String courseToDropCode = scanner.next();
                    Course courseToDrop = findCourse(courseToDropCode);
                    if (courseToDrop != null) {
                        student.dropCourse(courseToDrop);
                    } else {
                        System.out.println("Invalid course code.");
                    }
                    break;
                case 3:
                    System.out.println("\n=== Your Registered Courses ===");
                    for (Course c : student.getRegisteredCourses()) {
                        System.out.println(c);
                    }
                    break;
                case 4:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    public static void main(String[] args) {
        CourseRegistrationSystem system = new CourseRegistrationSystem();
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Display Courses");
            System.out.println("2. Student Registration");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    system.displayCourses();
                    break;
                case 2:
                    system.handleStudentRegistration(scanner);
                    break;
                case 3:
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);

        scanner.close();
    }
}