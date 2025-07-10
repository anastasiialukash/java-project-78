package hexlet.code.schemas;
import java.util.Objects;

/**
 * Schema for validating Integer values.
 */
public class NumberSchema extends BaseSchema<Integer> {
    /**
     * Requires that the value is non-null.
     * @return this schema instance for chaining
     */
    public NumberSchema required() {
        addValidator("required", Objects::nonNull);
        return this;
    }

    /**
     * Requires that the value is positive (> 0).
     * @return this schema instance for chaining
     */
    public NumberSchema positive() {
        addValidator("positive", val -> val == null || val > 0);
        return this;
    }

    /**
     * Requires that the value is within the specified inclusive range.
     * @param min Minimum allowed value
     * @param max Maximum allowed value
     * @return this schema instance for chaining
     */
    public NumberSchema range(int min, int max) {
        addValidator("range", val -> val >= min && val <= max);
        return this;
    }
}
