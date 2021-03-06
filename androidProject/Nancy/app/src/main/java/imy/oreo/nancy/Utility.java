package imy.oreo.nancy;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean isEmpty(String input) {

        return (input != null && input.trim().length() > 0 ? false : true);
    }

    public static boolean validateEmail(final String email) {

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
