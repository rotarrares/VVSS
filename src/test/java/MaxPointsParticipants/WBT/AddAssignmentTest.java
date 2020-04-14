package MaxPointsParticipants.WBT;

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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class AddAssignmentTest {

    StudentXMLRepository studentRepository;
    TemaXMLRepository temaRepository;
    NotaXMLRepository notaRepository;
    Service service;

    private void resetInputFiles(){
        String[] filenames = {"testStudenti.xml", "testTeme.xml", "testNote.xml"};
        String prefix = "src\\test\\java\\MaxPointsParticipants\\";

        for(int i = 0; i < filenames.length; i++){
            File file = new File(prefix + filenames[i]);

            if (file.exists())
                file.delete();

            try {
                File newFile = new File(prefix + filenames[i]);
                if(newFile.createNewFile()){
                    FileWriter myWriter = new FileWriter(prefix + filenames[i]);
                    myWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                            "<Entitati/>");
                    myWriter.close();
                }

            } catch (IOException e) {
                System.out.println("An error occurred. The could not reset the file");
                e.printStackTrace();
            }
        }
    }

    @Before
    public void initData(){
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        //make sure that the files are always clean
        //due to the fact we don't know how the system works and
        //if wrong data format is stored locally on failed tests
        //and data is corrupted
        resetInputFiles();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "src\\test\\java\\MaxPointsParticipants\\testStudenti.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "src\\test\\java\\MaxPointsParticipants\\testTeme.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "src\\test\\java\\MaxPointsParticipants\\testNote.xml");

        service = new Service(fileRepository1,fileRepository2,fileRepository3);
    }
    @Test
    public void testAddAssignmentIdValid(){
        String id = "1";
        String description = "tema_test";
        int startline = 1;
        int deadline = 3;
        assertEquals(service.saveTema(id, description, deadline, startline), 1);
    }

    @Test
    public void testAddAssignmentIdNotValid(){
        String id = null;
        String description = "tema_test";
        int startline = 1;
        int deadline = 3;
        assertEquals(service.saveTema(id, description, deadline, startline), 0);
    }
    @Test
    public void testAddAssignmentIdEmpty(){
        String id = "";
        String description = "tema_test";
        int startline = 1;
        int deadline = 3;
        assertEquals(service.saveTema(id, description, deadline, startline), 0);
    }
    @Test
    public void testAddAssignmentIdHasSpaces(){
        String id = "8 8 8";
        String description = "tema_test";
        int startline = 1;
        int deadline = 3;
        assertEquals(service.saveTema(id, description, deadline, startline), 0);
    }


    @Test
    public void testAddAssignmentDescriptionValid(){
        String id = "1";
        String description = "descriere";
        int startline = 1;
        int deadline = 3;
        assertEquals(service.saveTema(id, description, deadline, startline), 1);
    }
    @Test
    public void testAddAssignmentDescriptionNotValid(){
        String id = "1";
        String description = null;
        int startline = 1;
        int deadline = 3;
        assertEquals(service.saveTema(id, description, deadline, startline), 0);
    }
    @Test
    public void testAddAssignmentDescriptionEmpty(){
        String id = "1";
        String description = "";
        int startline = 1;
        int deadline = 3;
        assertEquals(service.saveTema(id, description, deadline, startline), 0);
    }
    @Test
    public void testAddAssignmentDescriptionTooShort(){
        String id = "1";
        String description = "q";
        int startline = 1;
        int deadline = 3;
        assertEquals(service.saveTema(id, description, deadline, startline), 0);
    }


    @Test
    public void testAddAssignmentStartlineValid(){
        String id = "1";
        String description = "descriere";
        int startline = 1;
        int deadline = 3;
        assertEquals(service.saveTema(id, description, deadline, startline), 1);
    }
    @Test
    public void testAddAssignmentStartlineInvalid(){
        String id = "1";
        String description = "descriere";
        int startline = -1;
        int deadline = 3;
        assertEquals(service.saveTema(id, description, deadline, startline), 0);
    }
    @Test
    public void testAddAssignmentStartlineZero(){
        String id = "1";
        String description = "descriere";
        int startline = 0;
        int deadline = 3;
        assertEquals(service.saveTema(id, description, deadline, startline), 0);
    }
    @Test
    public void testAddAssignmentStartlineTooHigh(){
        String id = "1";
        String description = "descriere";
        int startline = 15;
        int deadline = 16;
        assertEquals(service.saveTema(id, description, deadline, startline), 0);
    }
    @Test
    public void testAddAssignmentStartlineHigherThanDeadline(){
        String id = "1";
        String description = "descriere";
        int startline = 8;
        int deadline = 5;
        assertEquals(service.saveTema(id, description, deadline, startline), 0);
    }
    @Test
    public void testAddAssignmentStartlineLimit(){
        String id = "1";
        String description = "descriere";
        int startline = 1;
        int deadline = 5;
        assertEquals(service.saveTema(id, description, deadline, startline), 1);
    }



    @Test
    public void testAddAssignmentDeadlineValid(){
        String id = "1";
        String description = "descriere";
        int startline = 1;
        int deadline = 5;
        assertEquals(service.saveTema(id, description, deadline, startline), 1);
    }
    @Test
    public void testAddAssignmentDeadlineInvalid(){
        String id = "1";
        String description = "descriere";
        int startline = 1;
        int deadline = 0;
        assertEquals(service.saveTema(id, description, deadline, startline), 0);
    }
    @Test
    public void testAddAssignmentDeadlineTooHigh(){
        String id = "1";
        String description = "descriere";
        int startline = 1;
        int deadline = 15;
        assertEquals(service.saveTema(id, description, deadline, startline), 0);
    }
    @Test
    public void testAddAssignmentDeadlineLimit(){
        String id = "1";
        String description = "descriere";
        int startline = 1;
        int deadline = 14;
        assertEquals(service.saveTema(id, description, deadline, startline), 1);
    }


    @Test
    public void testAddAssignmentAlreadyInRepo(){
        String id = "1";
        String description = "descriere";
        int startline = 1;
        int deadline = 10;
        service.saveTema(id, description, deadline, startline);
        assertEquals(service.saveTema(id, description, deadline, startline), 0);
    }
    @Test
    public void testAddAssignmentUpperLimit(){
        String id = "1";
        String description = "descriere";
        int startline = 14;
        int deadline = 14;
        assertEquals(service.saveTema(id, description, deadline, startline), 1);
    }
    @Test
    public void testAddAssignmentLowerLimit(){
        String id = "1";
        String description = "descriere";
        int startline = 1;
        int deadline = 1;
        assertEquals(service.saveTema(id, description, deadline, startline), 1);
    }


}
