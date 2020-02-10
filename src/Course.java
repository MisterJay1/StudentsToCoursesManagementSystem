import java.sql.*;

public class Course {


    protected String courseName;
    protected int courseCredits;
    protected int maxStudents = 5;
    protected int maxNumberOfCourses = 4;
    protected Student[] students = new Student[maxStudents];
    protected int studentCounter;
    protected int studentIndex;

    private boolean studentFound;
    private int nrOfCourses;

    static protected String url;
    static protected String username = "alex";
    static protected String password = "alex";
    static protected Connection connection;
    static protected Statement stmt = null;
    static protected int limitConnection = 0;
    protected String tableName;


    public Course() {
        for (int i = 0; i < maxStudents; i++) {
            students[i] = new Student();
        }
        studentCounter = 0;

        if(limitConnection ==0) {
            this.url = "jdbc:mysql://localhost:3306/mathcourse";
            System.out.println("Connecting database...");
            try {
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Database connected!");
            } catch (SQLException e) {
                throw new IllegalStateException("Cannot connect the database!", e);
            }
            limitConnection++;
        }
    }

    public boolean findStudent(Student newStudent) {
        for (int i = 0; i < maxStudents; i++) {
            if (students[i].getName() == newStudent.getName()) {
                studentIndex = i;
                return true;
            }
        }
        return false;
    }

    public void registerStudent(Student newStudent) {
        System.out.println("");
        if (studentCounter == maxStudents) {
            System.out.println("Course " + this.courseName + " has reached the fully capacity.");
        } else {
            studentFound = findStudent(newStudent);
            if (studentFound == true) {
                System.out.println("Student " + newStudent.getName() + " is already registered at the " + this.courseName + " course.");
            } else {
                nrOfCourses = newStudent.getNumberOfCourses();
                if (nrOfCourses == maxNumberOfCourses) {
                    System.out.println("Student " + newStudent.getName() + " has full capacity of attended courses.");
                } else {
                    students[studentCounter] = newStudent;
                    students[studentCounter].increaseNumberOfCourses(this.courseName, this.courseCredits);
                    studentCounter++;
                    System.out.println("Student " + newStudent.getName() + " successfully registered at the " + this.courseName + " course.");

                    /* INSERT INTO DATABASE */
                    try {
                        stmt = connection.createStatement();
                        int rs = stmt.executeUpdate("INSERT INTO " +  this.tableName + "(StudentName, StudentID) VALUES ('" + newStudent.getName() + "', " + newStudent.getStudentID() + ")");
                        System.out.println("Insertion done!");

                    } catch (SQLException e) {
                        throw new IllegalStateException("Cannot insert into database!", e);
                    }
                }
            }
        }
    }

    public void removeStudent(Student newStudent) {
        System.out.println("");


        if (studentCounter == 0) {
            System.out.println("Course " + this.courseName + " has no attending students.");
        } else {
            studentFound = findStudent(newStudent);
            if (studentFound == false) {
                System.out.println("Student " + newStudent.getName() + " is not registered at the " + this.courseName + " course.");
            } else {
                studentCounter--;
                if (studentIndex == studentCounter) {
                    students[studentIndex].decreaseNumberOfCourses(this.courseName);
                    students[studentIndex] = new Student();
                } else {
                    students[studentIndex].decreaseNumberOfCourses(this.courseName);
                    for (int i = studentIndex; i < studentCounter; i++) {
                        students[i] = students[i + 1];
                    }
                    students[studentCounter] = new Student();
                }
                System.out.println("Student " + newStudent.getName() + " successfully removed from the " + this.courseName + " course.");

                /* Remove from the database */
                try {
                    stmt = connection.createStatement();
                    int rs = stmt.executeUpdate("DELETE FROM " +  this.tableName + " WHERE StudentID = "+ newStudent.getStudentID());
                    System.out.println("Deletion done!");

                } catch (SQLException e) {
                    throw new IllegalStateException("Cannot insert into database!", e);
                }
            }
        }
    }

    public void showStudents() {
        System.out.println("");
        if (studentCounter == 0) {
            System.out.println("Course " + this.courseName + " has no attending students.");
        } else {
            System.out.println("The following students attend the " + this.courseName + " course:");
            for (int i = 0; i < studentCounter; i++) {
                //System.out.println(students[i].getName());
            }

            /* Select from database */
            try {
                stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM " + this.tableName);
                //System.out.println("Selection done!");
                while (rs.next()) {
                    String studName = rs.getString("StudentName");
                    System.out.println(studName);
                }
            } catch (SQLException e ) {
                throw new IllegalStateException("Cannot select from database!", e);
            }
        }
    }

    public void putGrades(Student newStudent, int grade) {
        if (studentCounter == 0) {
            System.out.println("ERROR! Course " + this.courseName + " has no students. Cannot assign grades.");
        } else {
            studentFound = findStudent(newStudent);
            if (studentFound == false) {
                System.out.println("Student  " + newStudent.getName() + " is not registered at the " + courseName + " course!");
            }
            else {
                students[studentIndex].putGrade(this.courseName, grade);

                /* UPDATE DATABASE */
                try {
                    stmt = connection.createStatement();
                    int rs = stmt.executeUpdate("UPDATE " +  this.tableName + " SET StudentGrade = " + grade + " WHERE StudentID = " + newStudent.getStudentID());
                    System.out.println("Insertion done!");

                } catch (SQLException e) {
                    throw new IllegalStateException("Cannot insert into database!", e);
                }
            }
        }
    }

}
