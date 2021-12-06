package fr.ans.psc.delegate;

import com.mongodb.MongoServerException;
import com.mongodb.MongoWriteException;
import fr.ans.psc.api.PsApiDelegate;
import fr.ans.psc.exception.PscRequestException;
import fr.ans.psc.model.Error;
import fr.ans.psc.model.Ps;
import fr.ans.psc.model.PsRef;
import fr.ans.psc.repository.PsRefRepository;
import fr.ans.psc.repository.PsRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServlet;
import java.util.List;


@Service
@Slf4j
public class PsApiDelegateImpl extends AbstractApiDelegate implements PsApiDelegate {

    private final PsRepository psRepository;
    private final PsRefRepository psRefRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public PsApiDelegateImpl(PsRepository psRepository, PsRefRepository psRefRepository) {
        this.psRepository = psRepository;
        this.psRefRepository = psRefRepository;
    }

    @Override
    public ResponseEntity<Void> updatePsById(String psId, Ps ps) {
        Ps storedPs = psRepository.findByNationalId(psId);
        ps.set_id(storedPs.get_id());
        try {
            mongoTemplate.save(ps);
        } catch (MongoWriteException e) {
            log.info("Mongo server exception caught");
            Error error = new Error();
            error.setCode("500");
            error.setMessage("toto");
            throw new PscRequestException(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Ps> getPsById(String psId) {

        PsRef psRef = psRefRepository.findPsRefByNationalIdRef(psId);

        if (psRef == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String nationalId = psRef.getNationalId();

        Ps ps = psRepository.findByNationalId(nationalId);
        return new ResponseEntity<>(ps, HttpStatus.OK);
    }
}
