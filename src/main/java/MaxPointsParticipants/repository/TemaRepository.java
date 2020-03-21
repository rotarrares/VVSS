package MaxPointsParticipants.repository;

import MaxPointsParticipants.domain.Tema;
import MaxPointsParticipants.validation.*;

public class TemaRepository extends AbstractCRUDRepository<String, Tema> {
    public TemaRepository(Validator<Tema> validator) {
        super(validator);
    }
}

