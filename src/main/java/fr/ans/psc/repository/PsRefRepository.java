package fr.ans.psc.repository;

import fr.ans.psc.model.PsRef;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PsRefRepository extends MongoRepository<PsRef, ObjectId> {

    PsRef findPsRefByNationalIdRef(String nationalIdRef);
    List<PsRef> findAllByNationalId(String nationalId);
}
