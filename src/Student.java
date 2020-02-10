import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Student {


    protected static int studentIndex = 0;
    protected int studentID;
    protected String name;
    protected String tableName;

    private static int maxNumberOfCourses = 4;
    private static int totalNumberOfCourses = 5;
    private int numberOfCourses;
    private CourseTable[] CT = new CourseTable[totalNumberOfCourses];

    static protected String url;
    static protected String username = "alex";
    static protected String password = "alex";
    static protected Connection connection;
    static protected Statement stmt = null;
    static protected int limitConnection = 0;

    public Student(){
        this.numberOfCourses = 0;
        this.tableName = "students";
        name = "";

        for(int i = 0; i < totalNumberOfCourses; i++){
            CT[i] = new CourseTable();
            CT[i].registered = false;
        }

        CT[0].courseName = "Mathematics";
        CT[1].courseName = "Probability";
        CT[2].courseName = "Computer Architecture";
        CT[3].courseName = "Digital Electronics";
        CT[4].courseName = "Physics";
    }

    public Student(String studentName){
        this.name = studentName;
        this.tableName = "students";
        this.numberOfCourses = 0;

        for(int i = 0; i < totalNumberOfCourses; i++){
            CT[i] = new CourseTable();
            CT[i].registered = false;
        }

        CT[0].courseName = "Mathematics";
        CT[1].courseName = "Probability";
        CT[2].courseName = "Computer Architecture";
        CT[3].courseName = "Digital Electronics";
        CT[4].courseName = "Physics";

        studentIndex++;
        studentID = studentIndex;


        /* Connect TO DATABASE */
        if(limitConnection == 0) {
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


        /* INSERT INTO DATABASE */
        try {
            stmt = connection.createStatement();
            int rs = stmt.executeUpdate("INSERT INTO " +  this.tableName + "(StudentName, StudentID) VALUES ('" + this.name + "', " + this.studentID + ")");
            System.out.println("Insertion done!");

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot insert into database!", e);
        }

    }

    public String getName(){
        return this.name;
    }

    public int getNumberOfCourses(){
        return this.numberOfCourses;
    }

    public int getStudentID() {
        return this.studentID;
    }

    public void increaseNumberOfCourses(String currentCourseName, int courseCredits){
        this.numberOfCourses++;
        for (int i = 0; i < totalNumberOfCourses; i++){
            if(CT[i].courseName.equals(currentCourseName)){
                CT[i].registered = true;
                CT[i].credits = courseCredits;
                CT[i].courseIndex = this.numberOfCourses;

                /* INSERT INTO DATABASE */
                try {
                    stmt = connection.createStatement();
                    int rs = stmt.executeUpdate("UPDATE " +  this.tableName + " SET Course" + this.numberOfCourses +" ='" + currentCourseName + "' WHERE StudentID = " + this.studentID);
                    System.out.println("Insertion done!");

                } catch (SQLException e) {
                    throw new IllegalStateException("Cannot insert into database!", e);
                }

                break;
            }
        }
    }

    public void decreaseNumberOfCourses(String currentCourseName){

        for (int i = 0; i < totalNumberOfCourses; i++){
            if (CT[i].courseName.equals(currentCourseName)){
                CT[i].registered = false;

                /* UPDATE DATABASE */
                try {
                    stmt = connection.createStatement();
                    int rs = stmt.executeUpdate("UPDATE " +  this.tableName + " SET Course" + this.numberOfCourses + " = NULL WHERE StudentID = " + this.studentID);
                    System.out.println("Update done!");

                } catch (SQLException e) {
                    throw new IllegalStateException("Cannot insert into database!", e);
                }

                this.numberOfCourses--;
                break;
            }
        }
    }

    public void showCourses(){
        System.out.println("");
        if (numberOfCourses==0){
            System.out.println("Student " + this.name + " isn't registered at any courses.");
        }
        else{
            System.out.println("Student " + this.name + " is attending the following courses:");
            for (int i = 0; i < totalNumberOfCourses; i++){
                if (CT[i].registered == true) {
                    System.out.println(CT[i].courseName);
                }
            }
        }
    }

    public void putGrade(String currentCourseName, int courseGrade){
        for (int i = 0; i < totalNumberOfCourses; i++){
            if (CT[i].courseName.equals(currentCourseName)){
                CT[i].grade = courseGrade;

                /* Update Database with Course Grade */

                try {
                    stmt = connection.createStatement();
                    int rs = stmt.executeUpdate("UPDATE " +  this.tableName + " SET Grade" + this.CT[i].courseIndex +" ='" + courseGrade + "' WHERE StudentID = " + this.studentID);
                    System.out.println("Insertion done!");

                } catch (SQLException e) {
                    throw new IllegalStateException("Cannot insert into database!", e);
                }

                break;
            }
        }
    }


    public void showSemesterGrade(){
        System.out.println("");
        int sum = 0;
        int totalNumberOfCredits = 0;
        double semGrade;
        for (int i = 0; i < totalNumberOfCourses; i++){
            if (CT[i].registered == true) {
                sum += CT[i].grade * CT[i].credits;
                totalNumberOfCredits += CT[i].credits;
            }
        }
        semGrade = ((double)sum/(double)totalNumberOfCredits);
        System.out.println("Student " + this.name + " has a final semester grade: " + semGrade);

        /* Update database with the student's final grade */

        try {
            stmt = connection.createStatement();
            int rs = stmt.executeUpdate("UPDATE " +  this.tableName + " SET StudentFinalGrade = " + semGrade + " WHERE StudentID = " + this.studentID);
            System.out.println("Insertion done!");

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot insert into database!", e);
        }

    }
}
