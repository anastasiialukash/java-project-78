package hexlet.code;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidateTests {
    @ParameterizedTest(name = "{index} => input=\"{1}\", expected={2}")
    @MethodSource("validationProvider")
    void testValidation(StringSchema schema, String input, boolean expected) {
        assertEquals(expected, schema.isValid(input));
    }

    private static Stream<Arguments> validationProvider() {
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
}
