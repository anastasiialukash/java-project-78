package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class BaseSchema <T> {
    protected final List<Predicate<T>> rules = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public boolean isValid(Object inputToValidate) {
        try {
            T casted = (T) inputToValidate;
            return rules.stream().allMatch(rule -> rule.test(casted));
        } catch (ClassCastException e) {
            return false;
        }
    }
}
