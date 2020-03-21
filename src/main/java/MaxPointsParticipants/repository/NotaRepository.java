package MaxPointsParticipants.repository;

import MaxPointsParticipants.domain.*;
import MaxPointsParticipants.validation.*;

public class NotaRepository extends AbstractCRUDRepository<Pair<String, String>, Nota> {
    public NotaRepository(Validator<Nota> validator) {
        super(validator);
    }
}
