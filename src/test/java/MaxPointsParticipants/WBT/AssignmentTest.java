package MaxPointsParticipants.WBT;

import MaxPointsParticipants.repository.NotaXMLRepository;
import MaxPointsParticipants.repository.StudentXMLRepository;
import MaxPointsParticipants.repository.TemaXMLRepository;
import MaxPointsParticipants.service.Service;
import MaxPointsParticipants.validation.NotaValidator;
import MaxPointsParticipants.validation.StudentValidator;
import MaxPointsParticipants.validation.TemaValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class AssignmentTest {


    StudentXMLRepository studentRepository;
    TemaXMLRepository temaRepository;
    NotaXMLRepository notaRepository;
    Service service;

    @Before
    public void initData(){

        StudentValidator studentValidator = new StudentValidator();
        studentRepository = new StudentXMLRepository(studentValidator, "StudentiXML.xml");


        NotaValidator notaValidator = new NotaValidator();
        notaRepository = new NotaXMLRepository(notaValidator, "NotaXML.xml");


        TemaValidator temaLabValidator = new TemaValidator();
        temaRepository = new TemaXMLRepository(temaLabValidator, "TemaLaboratorXML.xml");

    }
    @Test
    public void testAddAssignmentIdValid(){

        try {
            service.saveTema("11", "tema_test", 6, 6);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
        assertEquals(temaRepository.findOne("11").getDescriere(), "tema_test");
    }
    @Test
    public void testAddAssignmentIdNotValid(){
        String[] params = {null, "tema_noId", "5", "5"};
        try{
            service.saveTema(null,"tema_noId", 5,5);
            Assert.fail("Nr tema invalid\n");
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        assertTrue(temaRepository.findOne("") ==  temaRepository.findOne(null));
    }
}
