package MaxPointsParticipants.repository;

import MaxPointsParticipants.domain.Student;
import MaxPointsParticipants.validation.*;

public class StudentRepository extends AbstractCRUDRepository<String, Student> {
    public StudentRepository(Validator<Student> validator) {
        super(validator);
    }
}

