import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the calculation logic in the ArccosCalculatorGui class.
 *
 * These tests verify the correctness of the from-scratch arccos implementation
 * against known mathematical values and edge cases.
 */
class ArccosCalculatorGuiTest {

  // A small tolerance for comparing double values.
  // This is necessary because floating-point arithmetic is not always exact.
  private static final double DELTA = 1e-9;

  @Test
  void testArccosOfOne() {
    // The arccosine of 1.0 should be 0.0 radians.
    // The first argument is the expected value.
    // The second argument is the value produced by our code.
    // The third is the tolerance (delta).
    assertEquals(0.0, ArccosCalculatorGui.computeArccos(1.0), DELTA);
  }

  @Test
  void testArccosOfZero() {
    // The arccosine of 0.0 should be PI / 2 radians.
    // We use Math.PI for a high-precision expected value.
    assertEquals(Math.PI / 2.0, ArccosCalculatorGui.computeArccos(0.0), DELTA);
  }

  @Test
  void testArccosOfNegativeOne() {
    // The arccosine of -1.0 should be PI radians.
    assertEquals(Math.PI, ArccosCalculatorGui.computeArccos(-1.0), DELTA);
  }

  @Test
  void testArccosOfPositiveValue() {
    // A common known value: arccos(0.5) is PI / 3 radians (or 60 degrees).
    assertEquals(Math.PI / 3.0, ArccosCalculatorGui.computeArccos(0.5), DELTA);
  }

  @Test
  void testArccosOfNegativeValue() {
    // A common known value: arccos(-0.5) is 2*PI / 3 radians (or 120 degrees).
    assertEquals(2.0 * Math.PI / 3.0, ArccosCalculatorGui.computeArccos(-0.5), DELTA);
  }
}