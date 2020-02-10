import java.sql.DriverManager;
import java.sql.SQLException;

public class CourseMathematics extends Course {
    public CourseMathematics(){
        super();
        this.courseName = "Mathematics";
        this.tableName = "mathix";
        this.courseCredits = 5;
    }
}
