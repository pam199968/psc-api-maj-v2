package fr.ans.psc.utils;

import fr.ans.psc.model.Ps;

import java.io.File;
import java.util.Date;

public class ApiUtils {

    public static Long getInstantTimestamp() {
        return new Date().getTime() / 1000;
    }

}
