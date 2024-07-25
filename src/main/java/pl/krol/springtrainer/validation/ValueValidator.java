package pl.krol.springtrainer.validation;

import jakarta.annotation.Nullable;
import pl.krol.springtrainer.annotations.AllowedValues;
import pl.krol.springtrainer.annotations.CurrencyCode;
import pl.krol.springtrainer.exceptions.UnallowedFieldValueException;
import pl.krol.springtrainer.exceptions.UnallowedNullFieldException;
import pl.krol.springtrainer.objects.CurrencyCodes;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ValueValidator {
    public static void validate(Object object) throws UnallowedFieldValueException, UnallowedNullFieldException, IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            if(field.isAnnotationPresent(AllowedValues.class)) {
                field.setAccessible(true);
                List<String> allowedValues = Arrays.stream(field.getAnnotation(AllowedValues.class).values()).toList();
                Object value = field.get(object);
                if(value == null && !field.isAnnotationPresent(Nullable.class)) {
                    throw new UnallowedNullFieldException(field.getName());
                }
                else if(value != null) {
                    validFields(allowedValues, field, value);
                }
            }
            if(field.isAnnotationPresent(CurrencyCode.class)) {
                field.setAccessible(true);
                List<String> validCodes = Arrays.stream(CurrencyCodes.values())
                        .map(Enum::name)
                        .toList();
                Object code = field.get(object);
                if(code == null) {
                    throw new UnallowedNullFieldException(field.getName());
                }
                validFields(validCodes, field, code);
            }
        }
    }

    private static void validFields(List<String> fields, Field currentField, Object comparison) throws UnallowedFieldValueException {
        for(String field : fields) {
            if(field.equals(comparison.toString())) {
                return;
            }
        }
        throw new UnallowedFieldValueException(currentField.getName());
    }
}
