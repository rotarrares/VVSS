package MaxPointsParticipants.validation;
import MaxPointsParticipants.domain.Student;

public class StudentValidator implements Validator<Student> {
    public void validate(Student student) throws ValidationException {

        if (student.getID() == null || student.getID().equals("")) {
            throw new ValidationException("ID invalid! \n");
        }
        if (student.getID().contains(" ") || !student.getID().matches("[a-zA-Z0-9]+")) {
            throw new ValidationException("ID invalid! \n");
        }

        if (student.getNume() == null || student.getNume().equals("")) {
            throw new ValidationException("Nume invalid! \n");
        }
        if(student.getNume().length() < 3  || student.getNume().length() > 20){
            throw new ValidationException("Incorrect name! \n");
        }
        if (!student.getNume().matches("[ a-zA-Z0-9]+")){
            throw new ValidationException("Incorrect name! \n");
        }

        if (student.getGrupa() <= 110 || student.getGrupa() >= 938) {
            throw new ValidationException("Grupa invalida! \n");
        }
    }
}

