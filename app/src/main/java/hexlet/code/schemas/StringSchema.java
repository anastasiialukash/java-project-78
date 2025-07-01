package hexlet.code.schemas;

public class StringSchema extends BaseSchema<String> {
    public StringSchema required() {
        rules.add(val -> val != null && !val.trim().isEmpty());
        return this;
    }

    public StringSchema minLength(int length) {
        rules.add(val -> val != null && val.length() >= length);
        return this;
    }

    public StringSchema contains(String substring) {
        rules.add(val -> val != null && val.contains(substring));
        return this;
    }
}
