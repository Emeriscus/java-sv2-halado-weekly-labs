package week01.webshop.utils;

import java.util.List;
import java.util.regex.Pattern;

public class UserValidator {

    public static boolean validateUser(String email, String password) {
        return isValidEmail(email) && isValidPassword(password);
    }

    public static boolean isValidEmail(String email) {

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=().])(?=\\S+$).{8,20}$";
        Pattern pat = Pattern.compile(passwordRegex);
        if (password == null) {
            return false;
        }
        return pat.matcher(password).matches();
    }

    public static boolean isBlank(String text) {
        return text == null || text.isBlank();
    }

    public static boolean isEmpty(List<String> stringList) {
        return stringList == null || stringList.isEmpty();
    }

    public static boolean isNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
