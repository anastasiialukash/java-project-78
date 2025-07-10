package hexlet.code.schemas;
import java.util.Objects;


public class NumberSchema extends BaseSchema<Integer> {
    public NumberSchema required() {
        addValidator("required", Objects::nonNull);
        return this;
    }

    public NumberSchema positive() {
        addValidator("positive", val -> val != null && val > 0);
        return this;
    }

    public NumberSchema range(int min, int max) {
        addValidator("range", val -> val != null && val >= min && val <= max);
        return this;
    }
}
