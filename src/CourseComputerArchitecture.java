import java.sql.DriverManager;
import java.sql.SQLException;

public class CourseComputerArchitecture extends Course {
    public CourseComputerArchitecture(){
        super();
        this.courseName = "Computer Architecture";
        this.tableName = "comparchitecture";
        this.courseCredits = 6;
    }
}
