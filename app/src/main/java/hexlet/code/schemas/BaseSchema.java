package hexlet.code.schemas;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public class BaseSchema<T> {
    protected final Map<String, Predicate<T>> rules = new LinkedHashMap<>();

    protected void addValidator(String key, Predicate<T> validator) {
        rules.put(key, validator);
    }

    public boolean isValid(T inputToValidate) {
        return rules.values().stream().allMatch(v -> v.test(inputToValidate));
    }
}
