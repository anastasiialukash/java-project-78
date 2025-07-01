package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;

public class MapSchema extends BaseSchema<Map<Object, Object>> {
    public MapSchema required() {
        rules.add(Objects::nonNull);
        return this;
    }

    public MapSchema sizeof(int size) {
        rules.add(val -> val != null && val.size() == size);
        return this;
    }
}
