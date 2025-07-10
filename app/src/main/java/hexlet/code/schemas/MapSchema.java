package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Schema for validating Map<String, T> values.
 * @param <T> Type of the map's values
 */
public class MapSchema<T> extends BaseSchema<Map<String, T>> {
    private final Map<String, BaseSchema<T>> shapeRules = new HashMap<>();

    /**
     * Requires that the map is non-null.
     * @return this schema instance for chaining
     */
    public MapSchema<T> required() {
        addValidator("required", Objects::nonNull);
        return this;
    }

    /**
     * Requires that the map has the specified size.
     * @param size Expected number of entries
     * @return this schema instance for chaining
     */
    public MapSchema<T> sizeof(int size) {
        addValidator("sizeof", val -> val != null && val.size() == size);
        return this;
    }

    /**
     * Defines the shape of the map: keys and their corresponding value schemas.
     * @param schemas Map of key names to their validation schemas
     * @return this schema instance for chaining
     */
    public MapSchema<T> shape(Map<String, BaseSchema<T>> schemas) {
        shapeRules.putAll(schemas);
        addValidator("shape", map -> {
            if (map == null) {
                return true;
            }
            for (var entry : shapeRules.entrySet()) {
                String key = entry.getKey();
                BaseSchema<T> schema = entry.getValue();
                if (!schema.isValid(map.get(key))) {
                    return false;
                }
            }
            return true;
        });
        return this;
    }
}
