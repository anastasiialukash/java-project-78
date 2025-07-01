package hexlet.code;

import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidateTests {
    @ParameterizedTest(name = "{index} => input=\"{1}\", expected={2}")
    @MethodSource("stringSchemaValidationProvider")
    void stringSchemaValidationTest(StringSchema schema, String input, boolean expected) {
        assertEquals(expected, schema.isValid(input));
    }

    @ParameterizedTest(name = "{index} => input=\"{1}\", expected={2}")
    @MethodSource("numberSchemaValidationProvider")
    void numberSchemaValidationTest(NumberSchema schema, Integer input, boolean expected) {
        assertEquals(expected, schema.isValid(input));
    }

    private static Stream<Arguments> stringSchemaValidationProvider() {
        return Stream.of(
                Arguments.of(new StringSchema().required(), null, false),
                Arguments.of(new StringSchema().required(), "", false),
                Arguments.of(new StringSchema().required(), "  ", false),
                Arguments.of(new StringSchema().required(), "abc", true),

                Arguments.of(new StringSchema().minLength(5), "abcd", false),
                Arguments.of(new StringSchema().minLength(3), "abcd", true),
                Arguments.of(new StringSchema().minLength(3), null, false),

                Arguments.of(new StringSchema().contains("test"), "this is a test", true),
                Arguments.of(new StringSchema().contains("foo"), "this is a test", false),
                Arguments.of(new StringSchema().contains("bar"), null, false),

                Arguments.of(new StringSchema().required().minLength(3).contains("lo"), "hello", true),
                Arguments.of(new StringSchema().required().minLength(3).contains("hi"), "hello", false)
        );
    }

    private static Stream<Arguments> numberSchemaValidationProvider() {
        return Stream.of(
                Arguments.of(new NumberSchema(), null, true),

                Arguments.of(new NumberSchema().required(), null, false),
                Arguments.of(new NumberSchema().required(), 5, true),

                Arguments.of(new NumberSchema().required().positive(), -10, false),
                Arguments.of(new NumberSchema().required().positive(), 0, false),
                Arguments.of(new NumberSchema().required().positive(), 10, true),

                Arguments.of(new NumberSchema().required().positive().range(5, 10), 5, true),
                Arguments.of(new NumberSchema().required().positive().range(5, 10), 10, true),
                Arguments.of(new NumberSchema().required().positive().range(5, 10), 4, false),
                Arguments.of(new NumberSchema().required().positive().range(5, 10), 11, false),
                Arguments.of(new NumberSchema().required().positive().range(5, 10), null, false)
        );
    }
}
