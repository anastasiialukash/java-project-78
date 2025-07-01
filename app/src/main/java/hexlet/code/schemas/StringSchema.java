package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class StringSchema {
    private final List<Predicate<String>> rules = new ArrayList<>();

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

    public boolean isValid(String stringToValidate) {
        return rules.stream().allMatch(rule -> rule.test(stringToValidate));
    }
}
