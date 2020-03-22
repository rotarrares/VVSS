package MaxPointsParticipants;

import static org.junit.Assert.assertTrue;

import MaxPointsParticipants.domain.Nota;
import MaxPointsParticipants.domain.Student;
import MaxPointsParticipants.domain.Tema;
import MaxPointsParticipants.repository.NotaXMLRepository;
import MaxPointsParticipants.repository.StudentXMLRepository;
import MaxPointsParticipants.repository.TemaXMLRepository;
import MaxPointsParticipants.service.Service;
import MaxPointsParticipants.validation.NotaValidator;
import MaxPointsParticipants.validation.StudentValidator;
import MaxPointsParticipants.validation.TemaValidator;
import MaxPointsParticipants.validation.Validator;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    /**
     * Rigorous Test :-)
        */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void testAddStudentService() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();
        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "C:\\\\Users\\rotar\\Desktop\\Projects\\vvss\\Lab2\\src\\test\\java\\MaxPointsParticipants\\studenti2.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "C:\\\\Users\\rotar\\Desktop\\Projects\\vvss\\Lab2\\src\\test\\java\\MaxPointsParticipants\\teme2.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "C:\\\\Users\\rotar\\Desktop\\Projects\\vvss\\Lab2\\src\\test\\java\\MaxPointsParticipants\\note2.xml");

        Service studentService = new Service(fileRepository1,fileRepository2,fileRepository3);
        studentService.saveTema("2", "De la mic la mare inaintam tare",31,1);
        studentService.saveStudent("1", "nume", 916);
        studentService.saveNota("1","2",32,20,"Formidable! :)");

        assertTrue(studentService.updateStudent("1","Ghencea Mihai",936)==1);
    }

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
