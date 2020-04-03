package MaxPointsParticipants.BBT;

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

import static org.junit.Assert.assertTrue;

public class StudentServiceTest {
    @Test
    public void testAddStudentService() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();
        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "C:\\\\Users\\rotar\\Desktop\\Projects\\vvss\\Lab2\\src\\test\\java\\MaxPointsParticipants\\studenti2.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "C:\\\\Users\\rotar\\Desktop\\Projects\\vvss\\Lab2\\src\\test\\java\\MaxPointsParticipants\\teme2.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "C:\\\\Users\\rotar\\Desktop\\Projects\\vvss\\Lab2\\src\\test\\java\\MaxPointsParticipants\\note2.xml");

        Service studentService = new Service(fileRepository1,fileRepository2,fileRepository3);
        studentService.saveTema("2", "test me daddy",31,1);
        studentService.saveStudent("1", "nume", 916);
        studentService.saveNota("1","2",32,20,"Formidable! :)");

        assertTrue(studentService.updateStudent("1","Ghencea Mihai",936)==1);
    }

}
