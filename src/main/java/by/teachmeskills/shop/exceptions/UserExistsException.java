package by.teachmeskills.shop.exceptions;

public class UserExistsException extends RegistrationException{
    public UserExistsException(String message) {
        super(message);
    }
}
