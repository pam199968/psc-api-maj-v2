package fr.ans.psc.utils;

import java.util.Date;

public class ApiUtils {

    public static Long getInstantTimestamp() {
        return new Date().getTime() / 1000;
    }

}
