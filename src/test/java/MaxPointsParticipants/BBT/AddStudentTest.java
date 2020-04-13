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
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

public class AddStudentTest {

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
    public void testAddStudent_EmptyId() {
        String id = "";
        String name = "andrei";
        int group = 936;
        assertEquals(this.service.saveStudent(id, name, group), 0);
    }

    @Test
    public void testAddStudent_EmptyName() {
        String id = "1";
        String name = "";
        int group = 936;
        assertEquals(this.service.saveStudent(id, name, group), 0);
    }

    @Test
    public void testAddStudent_NoGroup() {
        String id = "2";
        String name = "andrei";
        int group = 0;
        assertEquals(this.service.saveStudent(id, name, group), 0);
    }

    @Test
    public void testAddStudent_NegativeGroup() {
        String id = "3";
        String name = "andrei";
        int group = -1;
        assertEquals(this.service.saveStudent(id, name, group), 0);
    }

    @Test
    public void testAddStudent_WrongId() {
        String id = "[][][][]";
        String name = "andrei";
        int group = 936;
        assertEquals(this.service.saveStudent(id, name, group), 0);
    }

    @Test
    public void testAddStudent_WrongName() {
        String id = "4";
        String name = ",,,,,";
        int group = 936;
        assertEquals(this.service.saveStudent(id, name, group), 0);
    }

    @Test
    public void testAddStudent_LargeGroup() {
        String id = "5";
        String name = "andrei";
        int group = 999999999;
        assertEquals(this.service.saveStudent(id, name, group), 0);
    }

    @Test
    public void testAddStudent_LongName() {
        String id = "6";
        String name = "andreisabauandreisabauandreisabauandreisabauandreisabauandreisabau";
        int group = 936;
        assertEquals(this.service.saveStudent(id, name, group), 0);
    }

    @Test
    public void testAddStudent_SpacesInName() {
        String id = "7";
        String name = "andrei sabau";
        int group = 936;
        assertEquals(this.service.saveStudent(id, name, group), 1);
    }

    @Test
    public void testAddStudent_Success() {
        String id = "8";
        String name = "andrei";
        int group = 936;
        assertEquals(this.service.saveStudent(id, name, group), 1);
    }


}
