package model;

/**
 * Created by Agatha of Wood Beyond on 7/3/2014.
 */
public class Validator {

    public static final String emptyMess = "*Empty";
    public static final String spaceMess = "*No space allowed";
    public static final String lengthMess = "*Must be >= 3 chars";
    public static final String unavailableMess = "*Unavailable";

    public static boolean isEmpty(String input) {
        if (input.equals("Username...") || input.equals("Password...")) {
            return true;
        }
        return false;
    }

    public static boolean containSpace(String input) {
        String rex = ".*\\s+.*";
        if (input.matches(rex)) {
            return true;
        }
        return false;
    }

    public static boolean notLongEnough(String input) {
        if (input.length() < 3) {
            return true;
        }
        return false;
    }


    public static boolean usernameExist(String u) {
        for (int i = 0; i < Data.getAccounts().size(); i++) {
            if (Data.getAccounts().get(i).getUsername().equals(u)) {
                return true;
            }
        }
        return false;
    }
}
