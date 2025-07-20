package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;

/**
 * Schema for validating Map<String, T> values.
 * @param <T> Type of the map's values
 */
public class MapSchema<T> extends BaseSchema<Map<String, T>> {
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
     */
    public void shape(Map<String, BaseSchema<T>> schemas) {
        addValidator("shape", map -> {
            if (map == null) {
                return true;
            }

            for (var entry : schemas.entrySet()) {
                String key = entry.getKey();
                BaseSchema<T> schema = entry.getValue();
                T value = map.get(key);
                if (!schema.isValid(value)) {
                    return false;
                }
            }
            return true;
        });
    }
}
