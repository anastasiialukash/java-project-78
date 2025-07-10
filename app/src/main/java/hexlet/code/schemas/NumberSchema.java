package hexlet.code.schemas;
import java.util.Objects;


public class NumberSchema extends BaseSchema<Integer> {
    public NumberSchema required() {
        rules.add(Objects::nonNull);
        return this;
    }

    public NumberSchema positive() {
        rules.add(val -> val != null && val > 0);
        return this;
    }

    public NumberSchema range(int min, int max) {
        rules.add(val -> val != null && val >= min && val <= max);
        return this;
    }
}
