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
    public static Validator v = new Validator();

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
    void mapShapeValidationTest(Map<String, BaseSchema<?>> input, boolean expected) {
        MapSchema schema = v.map();

        Map<String, BaseSchema<?>> shapeDefinition = new HashMap<>();
        shapeDefinition.put("name", v.string().required());
        shapeDefinition.put("age", v.number().positive());

        schema.shape(shapeDefinition);

        assertEquals(expected, schema.isValid(input));
    }

    private static Stream<Arguments> stringSchemaValidationProvider() {
        return Stream.of(
                Arguments.of(v.string().required(), null, false),
                Arguments.of(v.string().required(), "", false),
                Arguments.of(v.string().required(), "  ", false),
                Arguments.of(v.string().required(), "abc", true),

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
        return Stream.of(
                Arguments.of(v.number(), null, true),

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
                Arguments.of(Map.of("name", "Alice", "age", 30), true),
                Arguments.of(Map.of("name", "", "age", 25), false),
                Arguments.of(Map.of("name", "Bob", "age", -5), false),
                Arguments.of(Map.of("age", 20), false),
                Arguments.of(Map.of("name", "Eva", "age", 1), true),
                Arguments.of(Map.of(), false)
        );
    }
}
