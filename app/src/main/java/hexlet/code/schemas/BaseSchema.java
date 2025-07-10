package hexlet.code.schemas;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Base class for validation schemas of type T.
 * Allows adding named validators, where the last one for each key overwrites the previous.
 * @param <T> Type of the value to be validated
 */
public class BaseSchema<T> {
    protected final Map<String, Predicate<T>> rules = new LinkedHashMap<>();

    /**
     * Adds or updates a validator with the specified key.
     * @param key Name of the validator
     * @param validator Predicate to test the value
     */
    protected void addValidator(String key, Predicate<T> validator) {
        rules.put(key, validator);
    }

    /**
     * Validates the value against all active validators.
     * @param inputToValidate Value to validate
     * @return true if all validations pass; false otherwise
     */
    public boolean isValid(T inputToValidate) {
        return rules.values().stream().allMatch(v -> v.test(inputToValidate));
    }
}
