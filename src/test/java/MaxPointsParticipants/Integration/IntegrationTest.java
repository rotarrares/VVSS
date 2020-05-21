package MaxPointsParticipants.Integration;

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
import org.junit.experimental.theories.internal.Assignments;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {

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
    public void test_addStudent() {
        String id = "1";
        String name = "andrei";
        int group = 936;
        assertEquals(this.service.saveStudent(id, name, group), 1);
    }

    @Test
    public void test_addAssignment(){
        String id = "tematest";
        String description = "tema1";
        int startline = 1;
        int deadline = 3;
        assertEquals(service.saveTema(id, description, deadline, startline), 1);
    }

    @Test
    public void test_addGrade(){
        String idStudent = "1";
        String idTema = "tematest";
        int valNota = 10;
        int predata = 4;
        String feedback = "ok";

        boolean studFound = false;
        Iterable<Student> students = service.findAllStudents();
        for(Student s: students)
            if (s.getID().equals(idStudent)) {
                studFound = true;
                break;
            }

        boolean assignmentFound = false;
        Iterable<Tema> teme = service.findAllTeme();
        for(Tema t: teme)
            if (t.getID().equals(idTema)) {
                assignmentFound = true;
                break;
            }

        int assert_value = -1;

        if (studFound && assignmentFound)
            assert_value = 1;

        assertEquals(service.saveNota(idStudent, idTema, valNota, predata, feedback), assert_value);
    }

    @Test
    public void BigBangIntegration() {
        test_addStudent();
        test_addAssignment();
        test_addGrade();
    }

    //End of BigBang Integration

    @Test
    public void test_addStudent_Incremental(){
        String id = "100";
        String name = "alex";
        int group = 900;
        assertEquals(this.service.saveStudent(id, name, group), 1);
    }

    //End of addStudent Incremental Integration


    @Test
    public void test_addAssignment_Incremental(){

        //top -> down
        //addAssignment
        String id = "primatema";
        String description = "tema1";
        int startline = 1;
        int deadline = 3;
        assertEquals(service.saveTema(id, description, deadline, startline), 1);

        //addStudent
        test_addStudent();
    }
    //End of addAssignment Incremental Integration

    @Test
    public void test_addGrade_Incremental(){

        //top -> down

        //ADD GRADE TESTING

        //add student
        String stud_id = "10";
        String stud_name = "alex";
        int stud_group = 900;
        this.service.saveStudent(stud_id, stud_name, stud_group);

        //add assignment
        String id = "tematest";
        String description = "tema1";
        int startline = 1;
        int deadline = 3;
        service.saveTema(id, description, deadline, startline);

        //add grade
        String idStudent = "10";
        String idTema = "tematest";
        int valNota = 10;
        int predata = 4;
        String feedback = "ok";
        assertEquals(service.saveNota(idStudent, idTema, valNota, predata, feedback), 1);

        //addAssignment
        test_addAssignment_Incremental();

        //addStudent
        test_addStudent_Incremental();

    }

    //End of addGrade Incremental Integration

}
