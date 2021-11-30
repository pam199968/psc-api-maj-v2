package fr.ans.psc.delegate.impl;

import fr.ans.psc.api.ExpertiseApiDelegate;
import fr.ans.psc.model.Expertise;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

public class ExpertiseApiDelegateImpl implements ExpertiseApiDelegate {
    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    @Override
    public ResponseEntity<Void> deleteExpertiseByExpertiseId(String psId, String exProId, String expertiseId) {
        return null;
    }

    @Override
    public ResponseEntity<List<Expertise>> getAllExpertisesByPsAndExPro(String psId, String exProId) {
        return null;
    }

    @Override
    public ResponseEntity<Expertise> getExpertiseByExpertiseId(String psId, String exProId, String expertiseId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> postExpertiseForPsAndExProId(String psId, String exProId, Expertise expertise) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateExpertiseByExpertiseId(String psId, String exProId, String expertiseId, Expertise expertise) {
        return null;
    }
}
