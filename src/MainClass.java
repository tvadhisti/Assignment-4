// ****************************************************************
// Tiva Adhisti Nafira Putri
// 2206046840
// ****************************************************************

import javax.swing.*;

public class MainClass {
    public static void main(String[] args) {
        // Initialize the frame
        JFrame frame = new JFrame("Infix -> Postfix Evaluator");

        // Set the position of the frame to the center of the screen
        frame.setLocationRelativeTo(null);

        // Set close operation to exit the program
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        new ExpressionEvaluatorGUI(frame);
    }
}
