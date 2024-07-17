package pl.krol.springtrainer.exceptions;

public class UnallowedFieldValueException extends Exception {
    public UnallowedFieldValueException(String fieldName) {
        super("Field '" + fieldName + "' contains unallowed values.");
    }
}
