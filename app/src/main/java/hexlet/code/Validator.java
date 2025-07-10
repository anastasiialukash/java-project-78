package hexlet.code;

import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;

/**
 * Factory for creating validation schemas.
 */
public class Validator {
    /**
     * Creates a new schema for strings.
     * @return a new StringSchema instance
     */
    public StringSchema string() {
        return new StringSchema();
    }

    /**
     * Creates a new schema for integers.
     * @return a new NumberSchema instance
     */
    public NumberSchema number() {
        return new NumberSchema();
    }

    /**
     * Creates a new schema for maps.
     * @return a new MapSchema instance
     */
    public MapSchema map() {
        return new MapSchema<>();
    }
}
