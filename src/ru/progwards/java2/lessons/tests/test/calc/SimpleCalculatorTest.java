package ru.progwards.java2.lessons.tests.test.calc;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import ru.progwards.java2.lessons.tests.SimpleCalculator;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleCalculatorTest {
    static SimpleCalculator simCalc;

    @BeforeAll
    static void init () {
        simCalc = new SimpleCalculator();
    }

    @ParameterizedTest
    @MethodSource({"paramSum"})
    public void sum (int sum, int val1, int val2) {
        assertEquals(sum, simCalc.sum(val1, val2));
    }

    static Stream<Arguments> paramSum() {
        return Stream.of(
                Arguments.of(Integer.MAX_VALUE, 0, Integer.MAX_VALUE),
                Arguments.of(0,0,0),
                Arguments.of(-1, Integer.MAX_VALUE, Integer.MIN_VALUE),
                Arguments.of(999, 555, 444),
                Arguments.of(0, -333, 333)
        );
    }

    @ParameterizedTest
    @ValueSource (ints = {Integer.MIN_VALUE,Integer.MIN_VALUE})
    public void sumExc (int val1) {
        assertThrows(ArithmeticException.class, () -> simCalc.sum(val1,val1));
    }

    @ParameterizedTest
    @MethodSource({"paramDiff"})
    public void sub (int diff, int val1, int val2) {
        assertEquals(diff, simCalc.diff(val1, val2));
    }

    static Stream <Arguments> paramDiff() {
        return Stream.of(
                Arguments.of(Integer.MIN_VALUE+1, 0, Integer.MAX_VALUE),
                Arguments.of(0,0,0),
                Arguments.of(0, Integer.MIN_VALUE, Integer.MIN_VALUE),
                Arguments.of(111, 555, 444),
                Arguments.of(-666, -333, 333)
        );
    }

    @ParameterizedTest
    @MethodSource({"paramDiffExc"})
    public void diffExc (int val1, int val2) {
        assertThrows(ArithmeticException.class, () -> simCalc.diff(val1,val2));
    }

    static Stream <Arguments> paramDiffExc() {
        return Stream.of(
                Arguments.of(Integer.MIN_VALUE, 1),
                Arguments.of(-2, Integer.MAX_VALUE)
        );
    }

    @ParameterizedTest
    @MethodSource({"paramMult"})
    public void mult (int diff, int val1, int val2) {
        assertEquals(diff, simCalc.mult(val1, val2));
    }

    static Stream <Arguments> paramMult() {
        return Stream.of(
                Arguments.of(Integer.MAX_VALUE, 1, Integer.MAX_VALUE),
                Arguments.of(0,0,0),
                Arguments.of(0, Integer.MIN_VALUE, 0),
                Arguments.of(4440, 10, 444),
                Arguments.of(-144, -12, 12)
        );
    }

    @ParameterizedTest
    @ValueSource (ints = {Integer.MIN_VALUE,Integer.MIN_VALUE})
    public void multExc (int val1) {
        assertThrows(ArithmeticException.class, () -> simCalc.mult(val1,2));
        assertThrows(ArithmeticException.class, () -> simCalc.mult(val1,-2));
    }

    @ParameterizedTest
    @MethodSource({"paramDiv"})
    public void div (int diff, int val1, int val2) {
        assertEquals(diff, simCalc.div(val1, val2));
    }

    static Stream <Arguments> paramDiv() {
        return Stream.of(
                Arguments.of(Integer.MAX_VALUE, Integer.MAX_VALUE, 1),
                Arguments.of(0,0,1),
                Arguments.of(0,0,-1),
                Arguments.of(1000, -1000, -1),
                Arguments.of(-1, Integer.MIN_VALUE, Integer.MAX_VALUE),
                Arguments.of(0, Integer.MAX_VALUE, Integer.MIN_VALUE)
        );
    }

    @ParameterizedTest
    @ValueSource (ints = {1, Integer.MIN_VALUE, Integer.MIN_VALUE})
    public void divExc (int val1) {
        assertThrows(ArithmeticException.class, () -> simCalc.div(val1,0));
    }

    @AfterAll
    static void destroy () {
        simCalc = null;
    }
}
