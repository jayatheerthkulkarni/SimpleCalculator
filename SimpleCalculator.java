import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A simple calculator application built using Java Swing.
 * It supports basic arithmetic operations, percentage, square, reciprocal, and a clear functionality.
 */
public class SimpleCalculator {
    private JFrame frame; // Main application window
    private JTextField display; // Text field to show input and results
    private String operator = ""; // Stores the current operator
    private double firstOperand = 0; // Stores the first operand of the operation
    private boolean isNewOperation = true; // Flag to indicate if a new operation has started

    // Constructor to initialize the calculator UI
    public SimpleCalculator() {
        // Create and set up the main frame
        frame = new JFrame("Simple Calculator");
        frame.setSize(300, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Set up the display field
        display = new JTextField();
        display.setFont(new Font("Arial", Font.PLAIN, 24)); // Font settings for the display
        display.setHorizontalAlignment(SwingConstants.RIGHT); // Align text to the right
        display.setEditable(false); // Make the display read-only
        frame.add(display, BorderLayout.NORTH); // Add display to the top of the frame

        // Create a panel for buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 10, 10)); // 5x4 grid layout with spacing
        frame.add(panel, BorderLayout.CENTER); // Add panel to the center of the frame

        // Array of button labels for the calculator
        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "%", "x^2", "1/x", "Clear"
        };

        // Create and add buttons to the panel
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 20)); // Font settings for buttons
            button.addActionListener(new ButtonClickListener()); // Add action listener
            panel.add(button); // Add button to the panel
        }

        // Make the frame visible
        frame.setVisible(true);
    }

    // Method to clear the display and reset the state
    private void clearDisplay() {
        display.setText("");
        operator = "";
        firstOperand = 0;
        isNewOperation = true;
    }

    // Method to append text to the display
    private void appendToDisplay(String text) {
        // If a new operation has started, clear the display
        if (isNewOperation) {
            display.setText("");
            isNewOperation = false;
        }
        // Append the text to the current display
        display.setText(display.getText() + text);
    }

    // Method to perform the calculation based on the operator
    private void calculate() {
        double secondOperand;
        try {
            // Parse the second operand from the display
            secondOperand = Double.parseDouble(display.getText());
        } catch (NumberFormatException e) {
            // Handle invalid input
            display.setText("Error");
            return;
        }

        double result = 0; // Variable to store the result
        switch (operator) {
            case "+": // Addition
                result = firstOperand + secondOperand;
                break;
            case "-": // Subtraction
                result = firstOperand - secondOperand;
                break;
            case "*": // Multiplication
                result = firstOperand * secondOperand;
                break;
            case "/": // Division
                if (secondOperand != 0) {
                    result = firstOperand / secondOperand;
                } else {
                    display.setText("Error"); // Handle division by zero
                    return;
                }
                break;
            case "%": // Modulo
                result = firstOperand % secondOperand;
                break;
        }
        // Display the result and reset for a new operation
        display.setText(String.valueOf(result));
        isNewOperation = true;
    }

    // Inner class to handle button click events
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand(); // Get the text of the clicked button

            if ("0123456789.".contains(command)) {
                // If the button is a digit or decimal point, append it to the display
                appendToDisplay(command);
            } else if ("+-*/%".contains(command)) {
                // If the button is an operator, store the operator and the first operand
                operator = command;
                firstOperand = Double.parseDouble(display.getText());
                isNewOperation = true;
            } else if (command.equals("=")) {
                // Perform the calculation for the "=" button
                calculate();
            } else if (command.equals("Clear")) {
                // Clear the display and reset the state
                clearDisplay();
            } else if (command.equals("x^2")) {
                // Calculate the square of the current value
                double value = Double.parseDouble(display.getText());
                display.setText(String.valueOf(value * value));
                isNewOperation = true;
            } else if (command.equals("1/x")) {
                // Calculate the reciprocal of the current value
                double value = Double.parseDouble(display.getText());
                if (value != 0) {
                    display.setText(String.valueOf(1 / value));
                } else {
                    display.setText("Error"); // Handle division by zero
                }
                isNewOperation = true;
            }
        }
    }

    // Main method to launch the calculator
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimpleCalculator::new); // Start the application on the Swing thread
    }
}
