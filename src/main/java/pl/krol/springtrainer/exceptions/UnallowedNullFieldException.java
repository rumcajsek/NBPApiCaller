package pl.krol.springtrainer.exceptions;

public class UnallowedNullFieldException extends NullPointerException {
    public UnallowedNullFieldException(String name) {
        super("Field '" + name + "' cannot be a null value");
    }
}
