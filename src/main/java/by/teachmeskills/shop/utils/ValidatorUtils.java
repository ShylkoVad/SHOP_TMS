package by.teachmeskills.shop.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtils {

    public static boolean validateRegistration(String email, String name, String surname, String password, String birthday) {
        return validateName(name) && validateSurname(surname)
                && validateBirthday(birthday)
                && validateEmail(email)
                && validatePassword(password);
    }

    private static boolean validateEmail(String email) {
        return email.matches("^(.+)@(\\S+)$");
    }

    private static boolean validateName(String name) {
        return name.matches("^[А-ЯЁ][а-яё]+$");
    }

    private static boolean validateSurname(String surname) {
        return surname.matches("^[А-ЯЁ][а-яё]+$");
    }

    private static boolean validatePassword(String password) {
        String PASS_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
        Pattern PATTERN = Pattern.compile(PASS_PATTERN);
        Matcher matcher = PATTERN.matcher(password);
        return matcher.matches();
    }

    private static boolean validateBirthday(String birthday) {
        return birthday.matches("^((19[0-9][0-9])|(200[0-9])-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01]))$");
    }
}
