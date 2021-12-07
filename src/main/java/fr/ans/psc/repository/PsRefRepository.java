package fr.ans.psc.repository;

import fr.ans.psc.model.PsRef;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PsRefRepository extends MongoRepository<PsRef, String> {

    PsRef findPsRefByNationalIdRef(String nationalIdRef);
    List<PsRef> findAllByNationalId(String nationalId);
}
