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


public class ArccosCalculatorGui extends JFrame {

  private static final double PI = 3.141592653589793;
  private static final int MAX_TAYLOR_SERIES_TERMS = 100;
  private static final double TAYLOR_SERIES_TOLERANCE = 1e-15;
  private static final int WINDOW_WIDTH = 500;
  private static final int WINDOW_HEIGHT = 300;
  private final JTextField inputField;
  private final JTextArea resultArea;


  public ArccosCalculatorGui() {
    super("Scientific Calculator - arccos(x)");
    setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null); // Center the window


    inputField = new JTextField(10);
    resultArea = new JTextArea(5, 30);
    resultArea.setEditable(false);
    resultArea.setLineWrap(true);
    resultArea.setWrapStyleWord(true);


    inputField.getAccessibleContext().setAccessibleName("Input value for arccos");
    inputField
        .getAccessibleContext()
        .setAccessibleDescription("Enter a number between -1 and 1");


    JPanel inputPanel = new JPanel(new FlowLayout());
    inputPanel.add(new JLabel("Enter x [-1 to 1]:"));
    inputPanel.add(inputField);


    JButton computeButton = new JButton("Compute arccos(x)");
    computeButton.getAccessibleContext().setAccessibleName("Compute");
    computeButton.addActionListener(
        e -> computeResult());
    inputPanel.add(computeButton);

    JPanel resultPanel = new JPanel(new BorderLayout());

    JScrollPane scrollPane = new JScrollPane(resultArea);
    resultPanel.add(scrollPane, BorderLayout.CENTER);

    setLayout(new BorderLayout());
    add(inputPanel, BorderLayout.NORTH);
    add(resultPanel, BorderLayout.CENTER);
  }


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
              "arccos(%.6f) = %.10f radians\n≈ %.6f degrees\nComputed in %.3f ms",
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


  private void showError(final String message) {
    resultArea.setText(""); // Clear previous results
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }


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


  private static double computeArcsin(final double x) {
    double sum = x;
    double term;

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

  private static double power(final double base, final int exp) {
    double result = 1.0;
    for (int i = 0; i < exp; i++) {
      result *= base;
    }
    return result;
  }

  private static double abs(final double value) {
    return (value < 0) ? -value : value;
  }

  private static double toDegrees(final double radians) {
    return radians * 180.0 / PI;
  }

  public static void main(final String[] args) {
    SwingUtilities.invokeLater(() -> new ArccosCalculatorGui().setVisible(true));
  }
}