package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MapSchema extends BaseSchema<Map<Object, Object>> {
    private final Map<String, BaseSchema<?>> shapeRules = new HashMap<>();

    public MapSchema required() {
        rules.add(Objects::nonNull);
        return this;
    }

    public MapSchema sizeof(int size) {
        rules.add(val -> val != null && val.size() == size);
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema<?>> schemas) {
        shapeRules.putAll(schemas);

        rules.add(map -> {
            if (map == null) {
                return true;
            }

            for (var entry : shapeRules.entrySet()) {
                String key = entry.getKey();
                BaseSchema<?> schema = entry.getValue();
                Object value = map.get(key);

                if (!schema.isValid(value)) {
                    return false;
                }
            }

            return true;
        });

        return this;
    }
}
