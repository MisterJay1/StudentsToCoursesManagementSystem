import java.sql.*;

public class Main {

    public static void main(String[] args){

        Course Mathematics = new CourseMathematics();
        Course Probability = new CourseProbability();
        Course ComputerArchitecture = new CourseComputerArchitecture();
        Course DigitalElectronics = new CourseDigitalElectronics();
        Course Physics = new CoursePhysics();

        Student Alex = new Student("Alex");
        Student Vlad = new Student("Vlad");
        Student Maria = new Student("Maria");
        Student Gigi = new Student("Gigi");
        Student Eugen = new Student("Eugen");
        Student Rares = new Student("Rares");
        Student Lavinia = new Student("Lavinia");
        Student Andrei = new Student("Andrei");
        Student Razvan = new Student("Razvan");
        Student Ariana = new Student("Ariana");
        Student George = new Student("George");
        Student Constantin = new Student("Constantin");


        Mathematics.registerStudent(Alex);
        Mathematics.registerStudent(Vlad);
        Mathematics.registerStudent(Maria);
        Mathematics.registerStudent(Gigi);
        Mathematics.registerStudent(Andrei);

        ComputerArchitecture.registerStudent(Alex);
        ComputerArchitecture.registerStudent(Rares);
        ComputerArchitecture.registerStudent(Lavinia);
        ComputerArchitecture.registerStudent(George);
        ComputerArchitecture.registerStudent(Constantin);

        DigitalElectronics.registerStudent(Vlad);
        DigitalElectronics.registerStudent(Alex);
        DigitalElectronics.registerStudent(Lavinia);
        DigitalElectronics.registerStudent(Razvan);
        DigitalElectronics.registerStudent(Eugen);

        Physics.registerStudent(Vlad);
        Physics.registerStudent(Andrei);
        Physics.registerStudent(Alex);
        Physics.registerStudent(Razvan);
        Physics.registerStudent(Eugen);

        Probability.registerStudent(Vlad);
        Probability.registerStudent(Alex);
        Probability.registerStudent(Lavinia);
        Probability.registerStudent(Razvan);
        Probability.registerStudent(Eugen);
        Probability.registerStudent(Vlad);



        Mathematics.showStudents();

        ComputerArchitecture.removeStudent(Gigi);
        Probability.removeStudent(Razvan);
        ComputerArchitecture.showStudents();


        Mathematics.putGrades(Gigi, 10);
        Mathematics.putGrades(Alex, 7);
        Mathematics.putGrades(Vlad, 9);
        Mathematics.putGrades(Maria, 8);
        Mathematics.putGrades(Andrei, 6);

        ComputerArchitecture.putGrades(Alex, 6);
        ComputerArchitecture.putGrades(Lavinia, 6);
        ComputerArchitecture.putGrades(Rares, 6);
        ComputerArchitecture.putGrades(George, 6);
        ComputerArchitecture.putGrades(Constantin, 6);

        DigitalElectronics.putGrades(Alex, 6);
        Physics.putGrades(Alex, 6);
        Probability.putGrades(Alex, 6);
        Probability.putGrades(Vlad, 8);

        Vlad.showSemesterGrade();
        Alex.showSemesterGrade();


        Mathematics.removeStudent(Alex);
        Mathematics.removeStudent(Vlad);
        Mathematics.removeStudent(Maria);
        Mathematics.removeStudent(Gigi);
        ComputerArchitecture.removeStudent(Rares);
        DigitalElectronics.removeStudent(Gigi);

    }

}
