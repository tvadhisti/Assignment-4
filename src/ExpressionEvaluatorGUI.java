// ****************************************************************
// Tiva Adhisti Nafira Putri
// 2206046840
// ****************************************************************

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ExpressionEvaluatorGUI {
    private JTextField infixField;
    private JLabel postfixField;
    private JLabel resultField;
    private JLabel errorMessageField;
    private JButton calculateButton;
    private ExpressionEvaluator evaluator;

    public ExpressionEvaluatorGUI(JFrame frame) {
        // Initialize the evaluator
        evaluator = new ExpressionEvaluator();

        // Initialize the panel and layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.setBackground(Color.LIGHT_GRAY);

        // Initialize the fields and labels
        infixField = new JTextField();
        postfixField = new JLabel();
        resultField = new JLabel();
        errorMessageField = new JLabel();

        // set the color of the fields
        infixField.setBackground(Color.YELLOW);

        // Set the size of the fields
        infixField.setPreferredSize(new Dimension(250, 30));
        postfixField.setPreferredSize(new Dimension(250, 30));
        resultField.setPreferredSize(new Dimension(250, 30));

        // Add listeners to the button
        infixField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Reset fields
                postfixField.setText("");
                resultField.setText("");
                errorMessageField.setText("[]");

                // Get the infix expression
                String infix = infixField.getText();

                // Convert to postfix and evaluate
                ExpressionEvaluator.PostfixNotation postfixNotation = evaluator.infixToPostfix(infix);
                String postfix = postfixNotation.expression;
                long result = postfixNotation.postfix;
                List<String> myList = postfixNotation.errorMessages;

                // Convert error messages to string
                StringBuilder errors = new StringBuilder();
                for (int i = 0; i < myList.size(); i++) {
                    errors.append(myList.get(i));
                    if (i < myList.size() - 1) {
                        errors.append(", ");
                    }
                }

                // Update fields
                postfixField.setText(postfix);
                resultField.setText(String.valueOf(result));
                if(errors.length() > 0) {
                    errorMessageField.setText(errors.toString());
                } else{
                    errorMessageField.setText("[]");
                }
            }
        });

        // Add components to the panel
        panel.add(new JLabel("Enter Infix Expression:"));
        panel.add(infixField);
        panel.add(new JLabel("Postfix Expression:"));
        panel.add(postfixField);
        panel.add(new JLabel("Result:"));
        panel.add(resultField);
        panel.add(new JLabel("Error Message:"));
        panel.add(errorMessageField);

        // Add panel to frame and set visibility
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
