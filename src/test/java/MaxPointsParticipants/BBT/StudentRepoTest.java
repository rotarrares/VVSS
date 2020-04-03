package MaxPointsParticipants.BBT;

import MaxPointsParticipants.domain.Student;
import MaxPointsParticipants.repository.StudentXMLRepository;
import MaxPointsParticipants.validation.StudentValidator;
import MaxPointsParticipants.validation.Validator;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class StudentRepoTest {

    @Test
    public void testAddStudentRepo() {

        Validator<Student> studentValidator = new StudentValidator();
        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "C:\\\\Users\\rotar\\Desktop\\Projects\\vvss\\Lab2\\src\\test\\java\\MaxPointsParticipants\\studenti2.xml");

        Student student = new Student("1", "nume", 916);
        fileRepository1.save(student);
        assertTrue(fileRepository1.findOne(student.getID()).equals(student));
        fileRepository1.delete(student.getID());
    }
}
