import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * A GUI application that calculates the arccosine of a given number x.
 *
 * <p>This calculator implements the arccosine function from scratch using a Taylor series
 * expansion for arcsin. It provides a simple user interface for input and displays the result in
 * both radians and degrees.
 */
public class ArccosCalculatorGui extends JFrame {

  /** The mathematical constant PI. */
  private static final double PI = 3.141592653589793;

  /** The maximum number of terms to use in the Taylor series expansion for arcsin. */
  private static final int MAX_TAYLOR_SERIES_TERMS = 100;

  /**
   * The tolerance for the Taylor series calculation. The calculation stops when a term's absolute
   * value is less than this tolerance.
   */
  private static final double TAYLOR_SERIES_TOLERANCE = 1e-15;

  /** The width of the main application window in pixels. */
  private static final int WINDOW_WIDTH = 500;

  /** The height of the main application window in pixels. */
  private static final int WINDOW_HEIGHT = 300;

  private final JTextField inputField;
  private final JTextArea resultArea;

  /**
   * Constructs the ArccosCalculatorGui and initializes all UI components.
   */
  public ArccosCalculatorGui() {
    super("Scientific Calculator - arccos(x)");
    setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLocationRelativeTo(null); // Center the window

    // --- UI Component Initialization ---
    inputField = new JTextField(10);
    resultArea = new JTextArea(5, 30);
    resultArea.setEditable(false);
    resultArea.setLineWrap(true);
    resultArea.setWrapStyleWord(true);

    // --- Accessibility Setup ---
    inputField.getAccessibleContext().setAccessibleName("Input value for arccos");
    inputField
        .getAccessibleContext()
        .setAccessibleDescription("Enter a number between -1 and 1");

    // --- Layout and Event Listener Setup ---
    JPanel inputPanel = new JPanel(new FlowLayout());
    inputPanel.add(new JLabel("Enter x [-1 to 1]:"));
    inputPanel.add(inputField);

    // CHANGED: Declaration moved closer to usage.
    JButton computeButton = new JButton("Compute arccos(x)");
    computeButton.getAccessibleContext().setAccessibleName("Compute");
    computeButton.addActionListener(
        e -> computeResult());
    inputPanel.add(computeButton);

    JPanel resultPanel = new JPanel(new BorderLayout());
    // CHANGED: Declaration moved closer to usage.
    JScrollPane scrollPane = new JScrollPane(resultArea);
    resultPanel.add(scrollPane, BorderLayout.CENTER);

    setLayout(new BorderLayout());
    add(inputPanel, BorderLayout.NORTH);
    add(resultPanel, BorderLayout.CENTER);
  }

  /**
   * Computes the arccosine of the input value when the "Compute" button is clicked. It handles
   * input parsing, validation, and displays the result or an error message.
   */
  private void computeResult() {
    String inputText = inputField.getText();
    try {
      double x = Double.parseDouble(inputText);

      if (x < -1.0 || x > 1.0) {
        throw new IllegalArgumentException("Input must be in the range [-1, 1].");
      }

      long startTime = System.nanoTime();
      double result = computeArccos(x);
      long endTime = System.nanoTime();

      double durationMs = (endTime - startTime) / 1_000_000.0;
      String output =
          String.format(
              "arccos(%.6f) = %.10f radians %n n≈ %.6f degrees %n Computed in %.3f ms",
              x, result, toDegrees(result), durationMs);
      resultArea.setText(output);

    } catch (NumberFormatException e) {
      showError("Invalid input: please enter a numeric value.");
    } catch (IllegalArgumentException e) {
      showError(e.getMessage());
    } catch (Exception e) {
      showError("An unexpected error occurred: " + e.getMessage());
    }
  }

  /**
   * Displays an error message in a dialog box.
   *
   * @param message The error message to display.
   */
  private void showError(final String message) {
    resultArea.setText(""); // Clear previous results
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Computes the arccosine of a number from scratch.
   *
   * <p>This implementation uses the identity arccos(x) = PI/2 - arcsin(x).
   *
   * @param x The number to compute the arccosine of, in the range [-1, 1].
   * @return The arccosine of x in radians.
   */
  private static double computeArccos(final double x) {
    if (x == 1.0) {
      return 0.0;
    }
    if (x == -1.0) {
      return PI;
    }
    if (x == 0.0) {
      return PI / 2.0;
    }
    return (PI / 2.0) - computeArcsin(x);
  }

  /**
   * Computes the arcsine of a number using a Taylor series expansion.
   *
   * @param x The number to compute the arcsine of.
   * @return The arcsine of x in radians.
   */
  private static double computeArcsin(final double x) {
    double sum = x;
    double term;
    // CHANGED: Renamed 'xPower' to 'powerOfX' for clarity and to satisfy Checkstyle.
    double powerOfX = x;

    for (int n = 1; n < MAX_TAYLOR_SERIES_TERMS; n++) {
      powerOfX *= x * x; // x^(2n+1)

      double numerator = factorial(2 * n);
      double denominator = power(4, n) * power(factorial(n), 2) * (2 * n + 1);
      term = (numerator / denominator) * powerOfX;
      sum += term;

      // Stop if the term is too small to make a difference
      if (abs(term) < TAYLOR_SERIES_TOLERANCE) {
        break;
      }
    }
    return sum;
  }

  /**
   * Calculates the factorial of an integer.
   *
   * @param n The non-negative integer.
   * @return The factorial of n.
   * @throws ArithmeticException if the result exceeds the limits of a double.
   */
  private static double factorial(final int n) {
    double result = 1.0;
    for (int i = 2; i <= n; i++) {
      result *= i;
      if (Double.isInfinite(result)) {
        throw new ArithmeticException("Factorial overflow during calculation.");
      }
    }
    return result;
  }

  /**
   * Calculates the power of a number.
   *
   * @param base The base number.
   * @param exp The non-negative integer exponent.
   * @return The result of base raised to the power of exp.
   */
  private static double power(final double base, final int exp) {
    double result = 1.0;
    for (int i = 0; i < exp; i++) {
      result *= base;
    }
    return result;
  }

  /**
   * Calculates the absolute value of a number.
   *
   * @param value The number.
   * @return The absolute value of the number.
   */
  private static double abs(final double value) {
    return (value < 0) ? -value : value;
  }

  /**
   * Converts an angle from radians to degrees.
   *
   * @param radians The angle in radians.
   * @return The angle in degrees.
   */
  private static double toDegrees(final double radians) {
    return radians * 180.0 / PI;
  }

  /**
   * The main entry point for the application.
   *
   * @param args Command line arguments (not used).
   */
  public static void main(final String[] args) {
    SwingUtilities.invokeLater(() -> new ArccosCalculatorGui().setVisible(true));
  }
}