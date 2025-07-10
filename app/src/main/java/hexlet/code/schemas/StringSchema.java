package hexlet.code.schemas;

public class StringSchema extends BaseSchema<String> {
    public StringSchema required() {
        addValidator("required", (val -> val != null && !val.trim().isEmpty()));
        return this;
    }

    public StringSchema minLength(int length) {
        addValidator("minLength", val -> val != null && val.length() >= length);
        return this;
    }

    public StringSchema contains(String substring) {
        addValidator("contains", val -> val != null && val.contains(substring));
        return this;
    }
}
