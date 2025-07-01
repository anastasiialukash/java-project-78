package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class BaseSchema <T> {
    protected final List<Predicate<T>> rules = new ArrayList<>();

    public boolean isValid(T inputToValidate) {
        return rules.stream().allMatch(rule -> rule.test(inputToValidate));
    }
}
