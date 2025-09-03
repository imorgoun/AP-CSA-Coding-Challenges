import java.text.DecimalFormat;
import java.util.function.DoubleUnaryOperator;

import static java.lang.Math.*;

public class MultivariableCalculus {
    // Declaring a custom function class to handle custom functions with any number of dimensions.
    @FunctionalInterface
    public interface MultivariableFunction { double apply(double[] arguments);}

    // Creating a decimal format to round results for better readability.
    static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.####");
    // Initializing a value for dx to handle differentiation.
    static double dx = 0.00001;

    // Initializing the test function.
    static MultivariableFunction myFunction = arguments -> pow(arguments[0], 2);

    public static void main(String[] args) {
        // Printing out the final x and y values after applying the gradient descent algorithm.
        System.out.println(DECIMAL_FORMAT.format(GradientDescent(myFunction, new double[] {1.7, -0.55}, 0.05, 100)[0]));
        System.out.println(DECIMAL_FORMAT.format(GradientDescent(myFunction, new double[] {1.7, -0.55}, 0.05, 100)[1]));

        // Integral testing.
        System.out.println(DECIMAL_FORMAT.format(singleIntegral(myFunction, new double[] {1, 2})));
    }

    // Function to calculate the partial derivative with respect to the argument with index j.
    public static double PartialDerivative(MultivariableFunction function, double[] arguments, int j) {
        // A duplicate of the arguments array.
        double[] arguments_dx = arguments.clone();
        // Adding dx to the argument with respect to which we are differentiating.
        arguments_dx[j] += dx;
        // Returns the value for the derivative calculated based on [f(x+h) - f(x)] / h formula for derivatives.
        return (function.apply(arguments_dx) - function.apply(arguments)) / dx;
    }

    // Gradient descent function, for finding the local minima or maxima of a function, using n iterations of adjustment.
    public static double[] GradientDescent(MultivariableFunction function, double[] arguments, double speed, int n) {
        // Loop for adjusting values n number of times.
        for (int i = 0; i < n; i++) {
            // Loops through each argument of the function to adjust them.
            for (int j = 0; j < arguments.length; j++) {
                // Adjusts the current argument based on the (not necessarily constant) speed times the derivative.
                arguments[j] -= speed * PartialDerivative(function, arguments, j);
            }
        }
        // Returns the updated values.
        return arguments;
    }

    public static double singleIntegral(MultivariableFunction function, double[] bounds) {
        double sum = 0;
        while (bounds[0] < bounds[1]) {
            sum += function.apply(new double[] {bounds[0]}) * dx;
            bounds[0] += dx;
        }
        return sum;
    }

    public static double MultivariableIntegral(MultivariableFunction function, double[][] bounds) {
        return 0;
    }
}
