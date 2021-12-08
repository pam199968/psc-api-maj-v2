package fr.ans.psc.utils;

import fr.ans.psc.model.PsRef;

import java.util.Date;

public class ApiUtils {

    public static Long getInstantTimestamp() {
        return new Date().getTime() / 1000;
    }

    public static boolean isPsRefActivated(PsRef psRef) {
        return psRef != null && psRef.getActivated() != null && (psRef.getDeactivated() == null || psRef.getActivated() > psRef.getDeactivated());
    }

}
