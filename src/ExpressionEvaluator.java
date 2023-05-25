// ****************************************************************
// Tiva Adhisti Nafira Putri
// 2206046840
// ****************************************************************

import java.util.*;

public class ExpressionEvaluator {
    // class to store infix expression, postfix result, and error messages
    public static class PostfixNotation {
        public final String expression;
        public final long postfix;
        public final List<String> errorMessages;

        public PostfixNotation(String expression, long postfix, List<String> errorMessages) {
            this.expression = expression;
            this.postfix = postfix;
            this.errorMessages = errorMessages;
        }
    }

    // Method to get precedence of operators
    private int getPrecedence(char ch) {
        switch (ch) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }

    // Method to perform operation
    private long performOperation(long a, long b, char op) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b != 0) {
                    return a / b;
                } else {
                    throw new UnsupportedOperationException("[Divide by zero]");
                }
            case '^':
                return (long) Math.pow(a, b);
        }
        return 0;
    }

    // Method to convert infix expression to postfix
    public PostfixNotation infixToPostfix(String infix) {
        String postfix = "";
        Stack<Character> stack = new Stack<>();
        List<String> errors = new ArrayList<>();

        // Process each character in the infix expression
        for (int i = 0; i < infix.length(); ++i) {
            char c = infix.charAt(i);
            if (c != ' ') {
                if (Character.isDigit(c)) {
                    if (i > 0 && Character.isDigit(infix.charAt(i - 1))) {
                        postfix += c;
                    } else {
                        postfix += ' ';
                        postfix += c;
                    }
                } else if (c == '(') {
                    stack.push(c);
                } else if (c == ')') {
                    // Pop and append all operators from the stack until we find an open parenthesis
                    while (!stack.isEmpty() && stack.peek() != '(') {
                        postfix += ' ';
                        postfix += stack.pop();
                    }

                    // Check if we found an open parenthesis
                    if (stack.isEmpty() || stack.peek() != '(')
                        errors.add("[Missing open parenthesis]");

                    else
                        stack.pop();
                } else {
                    // Pop and append all operators from the stack with higher
                    // or equal precedence than the current operator
                    while (!stack.isEmpty() && getPrecedence(c) <= getPrecedence(stack.peek())) {
                        int current = getPrecedence(c);
                        int previous = getPrecedence(stack.peek());

                        // If it's ^ then order from right to left
                        // if it's ( then we should push it to the stack
                        if (stack.peek() == '(' || (current == 3 && previous == 3))
                            break;

                        postfix += ' ';
                        postfix += stack.pop();
                    }
                    stack.push(c);
                }
            }
        }

        // Pop all the remaining operators from the stack
        while (!stack.isEmpty()) {
            if (stack.peek() == '(')
                errors.add("[Missing close parenthesis]");
            postfix += ' ';
            postfix += stack.pop();
        }

        // Evaluate the postfix expression
        PostfixNotation result;
        result = evaluatePostfix(postfix);
        errors.addAll(result.errorMessages);

        return new PostfixNotation(postfix.trim(), result.postfix, errors);
    }

    // Method to evaluate postfix expression
    public PostfixNotation evaluatePostfix(String postfix) {
        Stack<Long> stack = new Stack<>();
        StringTokenizer tokenizer = new StringTokenizer(postfix);
        List<String> errors = new ArrayList<>();

        // Process each token in the postfix expression
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            char firstChar = token.charAt(0);

            // If the token is a number, push it to the stack
            if (Character.isDigit(firstChar)) {
                stack.push(Long.parseLong(token));
            } else {
                // Pop two operands from the stack and perform the operation
                if(stack.isEmpty()){
                    errors.add("[Missing Operand]");
                    return new PostfixNotation("", 0, errors);
                }

                long b = stack.pop();

                if(stack.isEmpty()) {
                    errors.add("[Missing Operand]");
                    return new PostfixNotation("", b, errors);
                }

                long a = stack.pop();

                // Perform the operation
                long res;
                try{
                    res = performOperation(a, b, firstChar);
                } catch (UnsupportedOperationException e) {
                    errors.add(e.getMessage());
                    res = a;
                }

                stack.push(res);
            }
        }

        return new PostfixNotation("", stack.pop(), errors);
    }
}
