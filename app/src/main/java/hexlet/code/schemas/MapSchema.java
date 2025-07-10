package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MapSchema<T> extends BaseSchema<Map<String, T>> {
    private final Map<String, BaseSchema<T>> shapeRules = new HashMap<>();

    public MapSchema<T> required() {
        addValidator("required", Objects::nonNull);
        return this;
    }

    public MapSchema<T> sizeof(int size) {
        addValidator("sizeof", val -> val != null && val.size() == size);
        return this;
    }

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
