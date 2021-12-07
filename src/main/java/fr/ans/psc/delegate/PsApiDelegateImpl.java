package fr.ans.psc.delegate;

import fr.ans.psc.api.PsApiDelegate;
import fr.ans.psc.model.Ps;
import fr.ans.psc.model.PsRef;
import fr.ans.psc.repository.PsRefRepository;
import fr.ans.psc.repository.PsRepository;
import fr.ans.psc.utils.ApiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PsApiDelegateImpl extends AbstractApiDelegate implements PsApiDelegate {

    private final PsRepository psRepository;
    private final PsRefRepository psRefRepository;
    private final MongoTemplate mongoTemplate;

    public PsApiDelegateImpl(PsRepository psRepository, PsRefRepository psRefRepository, MongoTemplate mongoTemplate) {
        this.psRepository = psRepository;
        this.psRefRepository = psRefRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public ResponseEntity<Ps> getPsById(String psId) {

        PsRef psRef = psRefRepository.findPsRefByNationalIdRef(psId);

        // check if PsRef exists and is activated
        if (psRef == null ||
                ( psRef.getDeactivated() != null && psRef.getDeactivated() > psRef.getActivated())) {
            String operationLog = psRef == null ? "No Ps found with nationalIdRef {}" : "Ps {} is deactivated";
            log.warn(operationLog, psId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String nationalId = psRef.getNationalId();
        Ps ps = psRepository.findByNationalId(nationalId);
        log.info("Ps {} has been found", nationalId);
        return new ResponseEntity<>(ps, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> createNewPs(Ps ps) {
        // set mongo _id to avoid error if it's an update
        Ps storedPs = psRepository.findByNationalId(ps.getNationalId());
        if (storedPs != null) {
            log.info("Ps {} already exists, will be updated", ps.getNationalId());
            ps.set_id(storedPs.get_id());
        } else {
            log.info("PS {} doesn't exist already, will be created", ps.getNationalId());
        }

        mongoTemplate.save(ps);
        log.info("Ps {} succesfully stored or updated", ps.getNationalId());

        List<PsRef> psRefList = psRefRepository.findAllByNationalId(ps.getNationalId());
        long timestamp = ApiUtils.getInstantTimestamp();

        if (!psRefList.isEmpty()) {
            psRefList.stream().filter(psRef -> (psRef.getDeactivated() != null && psRef.getDeactivated() > psRef.getActivated()))
                    .forEach(psRef -> {
                        psRef.setActivated(timestamp);
                        mongoTemplate.save(psRef);
                        log.info("PsRef {} has been reactivated", psRef.getNationalIdRef());
                    });
        } else {
            // psRef could exist already and point to another Ps
            // (this shouldn't occur functionnally but could programmatically)
            // in this case we update the pointer
            // otherwise we just create it
            PsRef psRef = psRefRepository.findPsRefByNationalIdRef(ps.getNationalId());
            if (psRef != null) {
                psRef.setNationalId(ps.getNationalId());
            } else {
                psRef = new PsRef(ps.getNationalId(), ps.getNationalId(), timestamp);
            }

            mongoTemplate.save(psRef);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updatePs(Ps ps) {
        // check that the Ps exist AND that the psRef is activated
        List<PsRef> psRefList = psRefRepository.findAllByNationalId(ps.getNationalId());

        Ps storedPs = psRepository.findByNationalId(ps.getNationalId());
        if (storedPs == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ps.set_id(storedPs.get_id());
        mongoTemplate.save(ps);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deletePsById(String psId) {
        // get all PsRefs that point to this ps
        List<PsRef> psRefList = psRefRepository.findAllByNationalId(psId);
        if (psRefList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // deactivate each PsRef pointing to this ps
        long timestamp = ApiUtils.getInstantTimestamp();

        psRefList.forEach(psRef -> {
            psRef.setDeactivated(timestamp);
            mongoTemplate.save(psRef);
        });

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
