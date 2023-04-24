package ads.poo2.lab2.binaryTrees;

import java.util.*;
import java.util.function.BiFunction;

public class ExpressionTreeWithFunction extends BinaryTree<String> {
    private static final Map<String, BiFunction<Double, Double, Double>> OPERATORS =
            Map.of(
                    "+", Double::sum, // (a, b) -> a + b
                    "-", (a, b) -> a - b,
                    "*", (a, b) -> a * b,
                    "/", (a, b) -> a / b,
                    "^", Math::pow // (a, b) -> Math.pow(a, b)
            );


    private BiFunction<Double, Double, Double> operation;

    public static ExpressionTreeWithFunction fromExpression(String expression) {
        if (expression.isEmpty()) {
            throw new IllegalArgumentException("Expression can't be empty");
        }
        Deque<ExpressionTreeWithFunction> operands = new ArrayDeque<>();
        Deque<String> operators = new ArrayDeque<>();
        for (String token : expression.split(" ")) {
            if (token.isEmpty()) {
                continue;
            }
            switch (token) {
                case "(":
                    operators.push(token);
                    break;
                case ")":
                    while (true) {
                        assert operators.peek() != null; // if this fails, there is a mismatched parenthesis
                        if (Objects.equals(operators.peek(), "(")) break;
                        operands.push(applyOperator(operators.pop(), operands.pop(), operands.pop()));
                    }
                    //  remove the opening parenthesis
                    operators.pop();
                    break;
                default:
                    if (OPERATORS.containsKey(token)) {//  it's an operator
                        while (!operators.isEmpty() &&
                                isHigherPrecedence(token, operators.peek())) {
                            //  apply the operator on top of the operator stack to the operands
                            operands.push(applyOperator(operators.pop(), operands.pop(), operands.pop()));
                        }
                        operators.push(token);
                    } else {
                        //  it's a number
                        operands.push(new ExpressionTreeWithFunction(token));
                    }
            }
        }
        while (!operators.isEmpty()) {
            operands.push(applyOperator(operators.pop(), operands.pop(), operands.pop()));
        }
        return operands.pop();
    }

    private static ExpressionTreeWithFunction applyOperator(String operator, ExpressionTreeWithFunction right, ExpressionTreeWithFunction left) {
        return new ExpressionTreeWithFunction(operator, left, right);
    }

    /**
     * Returns true if 'operator1' has higher or same precedence as 'operator2',
     *
     * @param operator1
     * @param operator2
     * @return The method first checks if operator2 is an opening parenthesis ("(").
     * If it is, the method returns false, because operators inside parentheses have higher precedence than operators outside of them.
     * The relative precedence of the operators is determined by the following rules:
     * Multiplication (*) and division (/) have higher precedence than addition (+) and subtraction (-).
     * Operators with the same precedence are evaluated left to right.
     * Therefore, the method returns true if
     * operator1 is not multiplication or division, or
     * if operator2 is not addition or subtraction.
     * This ensures that multiplication and division are evaluated before addition and subtraction, as they have higher precedence.
     */
    private static boolean isHigherPrecedence(String operator1, String operator2) {
        if ("(".equals(operator2)) {
            return false;
        }
        return (!operator1.equals("*") && !operator1.equals("/"))
                || (!operator2.equals("+") && !operator2.equals("-"));
    }


    public ExpressionTreeWithFunction(String op, ExpressionTreeWithFunction left, ExpressionTreeWithFunction right) {
        super(op, left, right);
        initOP(op);
    }

    public ExpressionTreeWithFunction(String x) {
        super(x);
    }

    private void initOP(String op) {
        operation = OPERATORS.get(op);
        if (operation == null)
            throw new IllegalArgumentException("Unknown operator: " + op);
    }


    public static ExpressionTreeWithFunction read(String expression) {
        // Code to parse the expression and build the tree
        try (Scanner input = new Scanner(expression)) {
            return read(input);
        }
    }

    ////////////////////////////////////

    /**
     * Return an expression tree whose linear form
     * is given as the string 'inputString'
     */
    private static ExpressionTreeWithFunction read(Scanner input) {
        if (!input.hasNext())
            return null;
        String s = input.next();
        if (s.equals("$"))
            return null;
        if (s.endsWith("$"))
            return new ExpressionTreeWithFunction(s.substring(0, s.length() - 1));
        return new ExpressionTreeWithFunction(s, read(input), read(input));
    }

    public double evaluate() {
        if (operation != null) {
            return operation.apply(
                    ((ExpressionTreeWithFunction) left()).evaluate(),
                    ((ExpressionTreeWithFunction) right()).evaluate());
        } else {
            return Double.parseDouble(getData());
        }
    }

  /*     We prefere to see the expression as a binary tree
       public String toString() {
            if (operation != null) {
                return "(" + left() + " " + getData() + " " + right() + ")";
            } else {
                return getData();
            }
        }
   */


    //////////////////////////
    public static void main(String[] args) {
        //to simplify the code, we separate the tokens with a space
        ExpressionTreeWithFunction expression = ExpressionTreeWithFunction.fromExpression("( 1 + 2 ) * ( 3 + 4 )");
        System.out.println("expression : \n " + expression);
        System.out.println(expression.evaluate()); // Outputs: 21.0

        expression = ExpressionTreeWithFunction.fromExpression("1 + 2 * 3 + 4");
        System.out.println(expression);
        System.out.println(expression.evaluate()); // Outputs: 11.0
        ExpressionTreeWithFunction e = read("- * 2$ 5$ ^ 3$ 2$");
        System.out.println(e);
        System.out.println("\n(2 x 5) - (3 ^ 2) = " + e.evaluate() + " ==> expected " + 1.0 + "\n");
        e = read("+ 5$ * 2$ - 7$ 3$");
        System.out.println(e);
        System.out.println("\n5 + (2 x (7 - 3)) = " + e.evaluate() + " ==> expected " + 13.0 + "\n");
        e = read("- * / - 10$ 4$ 2$ 5$ + 2$ * 3$ 4$");
        System.out.println(e);
        System.out.println("\n((10 - 4) / 2) x 5) - (2 + (3 x 4)) = " + e.evaluate() + " ==> expected " + 1.0 + "\n");
    }
}
