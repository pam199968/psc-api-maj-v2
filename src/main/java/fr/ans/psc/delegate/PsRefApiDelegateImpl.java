package fr.ans.psc.delegate;

import fr.ans.psc.api.PsrefApiDelegate;
import fr.ans.psc.model.PsRef;
import fr.ans.psc.repository.PsRefRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PsRefApiDelegateImpl extends AbstractApiDelegate implements PsrefApiDelegate {

    private final PsRefRepository psRefRepository;

    public PsRefApiDelegateImpl(PsRefRepository psRefRepository) {
        this.psRefRepository = psRefRepository;
    }

    @Override
    public ResponseEntity<PsRef> getPsrefById(String psrefId) {
        PsRef psref = psRefRepository.findPsRefByNationalIdRef(psrefId);

        if (psref == null) {
            log.warn("PsRef not found with nationalIdRef {}", psrefId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            log.info("PsRef {} has been found", psrefId);
            return new ResponseEntity<>(psref, HttpStatus.OK);
        }
    }
}
