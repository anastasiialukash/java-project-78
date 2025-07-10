package hexlet.code.schemas;

/**
 * Schema for validating String values.
 */
public class StringSchema extends BaseSchema<String> {
    /**
     * Requires that the string is non-null and not empty after trimming.
     * @return this schema instance for chaining
     */
    public StringSchema required() {
        addValidator("required", (val -> val != null && !val.trim().isEmpty()));
        return this;
    }

    /**
     * Requires that the string has at least the specified length.
     * @param length Minimum number of characters
     * @return this schema instance for chaining
     */
    public StringSchema minLength(int length) {
        addValidator("minLength", val -> val != null && val.length() >= length);
        return this;
    }

    /**
     * Requires that the string contains the given substring.
     * @param substring Substring that must be present
     * @return this schema instance for chaining
     */
    public StringSchema contains(String substring) {
        addValidator("contains", val -> val != null && val.contains(substring));
        return this;
    }
}
