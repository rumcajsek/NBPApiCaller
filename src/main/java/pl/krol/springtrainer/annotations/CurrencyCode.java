package pl.krol.springtrainer.annotations;

import pl.krol.springtrainer.objects.CurrencyCodes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrencyCode {
}
