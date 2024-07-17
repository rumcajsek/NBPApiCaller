package pl.krol.springtrainer.annotations;

import jakarta.annotation.Nullable;
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
                String[] allowedValues = field.getAnnotation(AllowedValues.class).values();
                Object value = field.get(object);
                if(value == null && !field.isAnnotationPresent(Nullable.class)) {
                    throw new UnallowedNullFieldException(field.getName());
                }
                else if(value != null) {
                    boolean valid = false;
                    for (String allowedValue : allowedValues) {
                        if (allowedValue.equals(value.toString())) {
                            valid = true;
                            break;
                        }
                    }
                    if (!valid) {
                        throw new UnallowedFieldValueException(field.getName());
                    }
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
                boolean valid = false;
                for(String currencyCode : validCodes) {
                    if(currencyCode.equals(code.toString())) {
                        valid = true;
                        break;
                    }
                }
                if(!valid) {
                    throw new UnallowedFieldValueException(field.getName());
                }
            }
        }
    }
}
