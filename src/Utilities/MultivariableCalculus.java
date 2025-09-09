package Utilities;

import java.text.DecimalFormat;
import static java.lang.Math.*;

public class MultivariableCalculus {
    /** Presets */
    // Declaring a custom function object to handle custom functions with any number of arguments.
    @FunctionalInterface
    public interface MultivariableFunction {
        // Initializing the function to use an array of arguments with any length.
        double apply(double[] args);
        // Adding a built-in "add" function to combine multiple functions together through addition.
        default MultivariableFunction add(MultivariableFunction other) {
            // Returns the sum of the two functions.
            return args -> this.apply(args) + other.apply(args);
        }
        // Adding a subtraction function because you cannot use negative addition in this case.
        default MultivariableFunction subtract(MultivariableFunction other) {
            // Returns the difference of the two functions.
            return args -> this.apply(args) - other.apply(args);
        }
        // Adding built-in multiplication capability.
        default MultivariableFunction multiply(MultivariableFunction other) {
            // Returns the product of the two functions.
            return args -> this.apply(args) * other.apply(args);
        }
    }

    // Creating a decimal format to round results for better readability.
    static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.####");
    // Initializing a value for dx to handle differentiation.
    static double dx = 0.00001;

    // Initializing the test function(s).
    static MultivariableFunction myFunction = args -> sin(args[0]) + cos(args[1]);

    /** Main */
    // Using main function to test.
    public static void main(String[] args) {
        // Gradient descent testing.
        MultivariableFunction funcMINUS = DensityFunction2D(new double[] {1, 2});
        MultivariableFunction funcPLUS = DensityFunction2D(new double[] {5, 6});
        funcPLUS = funcPLUS.subtract(funcMINUS);
        MultivariableFunction finalFuncPLUS = funcPLUS;
        MultivariableFunction funcINT = argsK -> IteratedIntegral(finalFuncPLUS, new double[] {0, argsK[0]}, 1);

        System.out.println(DECIMAL_FORMAT.format(funcPLUS.apply(new double[] {4})));
        System.out.println("Final x value : " + DECIMAL_FORMAT.format(GradientDescent(funcINT, new double[] {0}, 0.5, 10)[0]));

        // Integral testing.
        System.out.println("Integral from 0 to 2 of x^2 : " + DECIMAL_FORMAT.format(IteratedIntegral(myFunction, new double[] {0, 2 * PI}, 2)));
        System.out.println(DECIMAL_FORMAT.format(DefiniteIntegral(myFunction, new double[] {0, 2})));

        // Density field testing.
        MultivariableFunction testDensityFunction = DensityFunction2D(new double[] {0, 1, 2, 3});
        System.out.println(DECIMAL_FORMAT.format(PartialDerivative(testDensityFunction, new double[] {0}, 0)));
        System.out.println(DECIMAL_FORMAT.format(GradientDescent(testDensityFunction, new double[] {0}, 0.1, 100)[0]));
    }

    /** Differentiation */
    // Function to calculate the partial derivative with respect to the argument with index j.
    public static double PartialDerivative(MultivariableFunction function, double[] args, int j) {
        // A duplicate of the arguments array.
        double[] args_dx = args.clone();
        // Adding dx to the argument with respect to which we are differentiating.
        args_dx[j] += dx;
        // Returns the value for the derivative calculated based on [f(x+h) - f(x)] / h formula for derivatives.
        return (function.apply(args_dx) - function.apply(args)) / dx;
    }

    // Gradient descent function, for finding the local minima or maxima of a function, using n iterations of adjustment.
    public static double[] GradientDescent(MultivariableFunction function, double[] args, double speed, int n) {
        // Loop for adjusting values n number of times.
        for (int i = 0; i < n; i++) {
            // Loops through each argument of the function to adjust them.
            for (int j = 0; j < args.length; j++) {
                // Adjusts the current argument based on the (not necessarily constant) speed times the derivative.
                args[j] -= speed * PartialDerivative(function, args, j);
            }
        }
        // Returns the updated values.
        return args;
    }

    /** Integration */
    public static double DefiniteIntegral(MultivariableFunction function, double[] bounds, int j) {
        double sum = 0.0;
        double[] args = new double[] {};
        args[j] = bounds[0];
        while (bounds[0] < bounds[1]) {
            sum += function.apply(bounds) * dx;
            bounds[0] += dx;
        }
        return sum;
    }
    public static MultivariableFunction IndefiniteIntegral(MultivariableFunction function, double[] domain) {
        return function;
    }

    // Function used to calculate the nth integral of a function.
    public static double IteratedIntegral(MultivariableFunction function, double[] bounds, int n) {
        // Initializing the value of the compressed integral as zero.
        double runningSum = 0;
        // Initializing the value of the factorial as one.
        double outerFactorial = 1;
        // Looping over all the values from 1 to n.
        for (int i = 1; i < n + 1; i++) {
            // Calculating the factorial.
            outerFactorial *= i;
        }
        // Initializing the compressed integral function.
        MultivariableFunction compressedIntegralFunction = args -> pow(bounds[1] - args[0], n - 1);
        // Multiplying the compressed integral function by the original to get the single integral form.
        compressedIntegralFunction = compressedIntegralFunction.multiply(function);
        // Calculating the single integral using a loop over the lower bound.
        while (bounds[0] < bounds[1]) {
            // Adding the value of the function at the lower bound multiplied by dx to the running sum.
            runningSum += compressedIntegralFunction.apply(bounds) * dx;
            // Incrementing the lower bound.
            bounds[0] += dx;
        }
        // Returns the complete sum by multiplying the reciprocal of the factorial time the value of the single integral.
        return runningSum * (1 / outerFactorial);
    }

    /** Density Fields */
    // Function for generating density values based on points on one line.
    public static MultivariableFunction DensityFunction2D(double[] points) {
        // Empty density function.
        MultivariableFunction densityFunction = args -> 0.0;
        // Loops through each point in pointCoordinates.
        for (double displacement : points) {
            // Initializes the contribution function for each displacement.
            MultivariableFunction contribution = args -> Math.exp(-Math.pow(args[0] - displacement, 2));
            // Adds the contribution for each point to the total density function.
            densityFunction = densityFunction.add(contribution);
        }
        // Returns the sum of all the contributions.
        return densityFunction;
    }

    public static MultivariableFunction DensityFunction3D(double[][] points) {
        MultivariableFunction densityFunction = args -> 0.0;
        for (double[] point : points) {
            MultivariableFunction densityContributionFunction = args -> exp(-pow(args[0] - point[0], 2)) * exp(-pow(args[1] - point[1], 2));
            densityFunction = densityFunction.add(densityContributionFunction);
        }
        return densityFunction;
    }
}
