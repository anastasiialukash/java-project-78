package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidateTests {
    @ParameterizedTest(name = "{index} => input=\"{1}\", expected={2}")
    @MethodSource("stringSchemaValidationProvider")
    void stringSchemaValidationTest(BaseSchema<String> schema, String input, boolean expected) {
        assertEquals(expected, schema.isValid(input));
    }

    @ParameterizedTest(name = "{index} => input=\"{1}\", expected={2}")
    @MethodSource("numberSchemaValidationProvider")
    void numberSchemaValidationTest(BaseSchema<Integer> schema, Integer input, boolean expected) {
        assertEquals(expected, schema.isValid(input));
    }

    @ParameterizedTest(name = "{index} => input=\"{1}\", expected={2}")
    @MethodSource("mapValidationProvider")
    void mapSchemaValidationTest(BaseSchema<Map<Object, Object>> schema, Map<Object, Object> input, boolean expected) {
        assertEquals(expected, schema.isValid(input));
    }

    @ParameterizedTest(name = "{index} => input={0}, expected={1}")
    @MethodSource("shapeValidationProvider")
    void mapShapeValidationTest(Map<String, String> input, boolean expected) {
        Validator v = new Validator();
        MapSchema schema = v.map();

        Map<String, BaseSchema<String>> shapeDefinition = new HashMap<>();
        shapeDefinition.put("firstName", v.string().required());
        shapeDefinition.put("lastName", v.string().required().minLength(2));

        schema.shape(shapeDefinition);

        assertEquals(expected, schema.isValid(input));
    }

    private static Stream<Arguments> stringSchemaValidationProvider() {
        Validator v = new Validator();
        return Stream.of(
                Arguments.of(v.string().required(), null, false),
                Arguments.of(v.string().required(), "", false),
                Arguments.of(v.string().required(), "  ", false),
                Arguments.of(v.string().required(), "abc", true),
                Arguments.of(v.string(), "", true),
                Arguments.of(v.string(), null, true),

                Arguments.of(v.string().minLength(5), "abcd", false),
                Arguments.of(v.string().minLength(3), "abcd", true),
                Arguments.of(v.string().minLength(3), null, false),

                Arguments.of(v.string().contains("test"), "this is a test", true),
                Arguments.of(v.string().contains("foo"), "this is a test", false),
                Arguments.of(v.string().contains("bar"), null, false),

                Arguments.of(v.string().required().minLength(3).contains("lo"), "hello", true),
                Arguments.of(v.string().required().minLength(3).contains("hi"), "hello", false)
        );
    }

    private static Stream<Arguments> numberSchemaValidationProvider() {
        Validator v = new Validator();
        return Stream.of(
                Arguments.of(v.number(), null, true),
                Arguments.of(v.number(), 123, true),
                Arguments.of(v.number(), -123, true),
                Arguments.of(v.number().positive(), null, true),

                Arguments.of(v.number().required(), null, false),
                Arguments.of(v.number().required(), 5, true),

                Arguments.of(v.number().required().positive(), -10, false),
                Arguments.of(v.number().required().positive(), 0, false),
                Arguments.of(v.number().required().positive(), 10, true),

                Arguments.of(v.number().required().positive().range(5, 10), 5, true),
                Arguments.of(v.number().required().positive().range(5, 10), 10, true),
                Arguments.of(v.number().required().positive().range(5, 10), 4, false),
                Arguments.of(v.number().required().positive().range(5, 10), 11, false),
                Arguments.of(v.number().required().positive().range(5, 10), null, false)
        );
    }

    private static Stream<Arguments> mapValidationProvider() {
        Validator v = new Validator();
        return Stream.of(
                Arguments.of(v.map().required(), null, false),
                Arguments.of(v.map().required(), Map.of(), true),
                Arguments.of(v.map(), null, true),
                Arguments.of(v.map().sizeof(0), Map.of(), true),
                Arguments.of(v.map().sizeof(2), Map.of("a", 1), false),
                Arguments.of(v.map().sizeof(2), Map.of("a", 1, "b", 2), true),
                Arguments.of(v.map().required().sizeof(1), Map.of(), false),
                Arguments.of(v.map().required().sizeof(1), Map.of("key", "val"), true)
        );
    }

    private static Stream<Arguments> shapeValidationProvider() {
        return Stream.of(
                Arguments.of(Map.of("firstName", "Alice", "lastName", "King"), true),
                Arguments.of(Map.of("firstName", "", "lastName", "King"), false),
                Arguments.of(Map.of("firstName", "Bob", "lastName", "1"), false),
                Arguments.of(Map.of("firstName", "Eva", "lastName", "Duke"), true),
                Arguments.of(Map.of(), false)
        );
    }
}
